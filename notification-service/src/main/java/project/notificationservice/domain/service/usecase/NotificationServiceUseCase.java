package project.notificationservice.domain.service.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import project.notificationservice.domain.dto.response.NotificationResponse;
import project.notificationservice.domain.entity.Notification;
import project.notificationservice.domain.repository.EmitterRepository;
import project.notificationservice.domain.repository.NotificationRepository;
import project.notificationservice.domain.service.NotificationService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceUseCase implements NotificationService {

    private static final Long DEFAULT_TIMEOUT = 1000L * 60 * 30; //30분
    public static final String PREFIX_URL = "khyu2.store";
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;


    /**
     * SSE연결 및 미수신 이벤트 재전송 처리
     * @param lastEventId lastEventId
     * @param userId 유저id
     * @return
     */
    @Override
    @Transactional
    public SseEmitter subscribe(String lastEventId, Long userId) {
        int memberNo = userId.intValue();
        //고유 emitter ID 생성(회원 번호 + 현재 시간)
        String id = memberNo + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));

        log.info("Emitter 추가: {}", emitter);

        // 연결 종료, 타임아웃 발생 시 해당 emitter 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(id));
        emitter.onTimeout(() -> emitterRepository.deleteById(id));

        sendToClient(emitter, id, "EventStream Created [userId=" + memberNo + "]");

        if (lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllCacheStartWithId(String.valueOf(memberNo));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    @Override
    public List<NotificationResponse> findAllById(Long memberNo) {
        List<Notification> notifications = notificationRepository.findByReceiver(memberNo.intValue());
        return notifications.stream()
                .map(NotificationResponse::from)
                .sorted(Comparator.comparing(NotificationResponse::id).reversed())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void readNotifications(Long id) {
        notificationRepository.updateIsReadById(id);
    }

    @Override
    @Transactional
    public void deleteNotification(Long[] idList) {
        for (Long id : idList) {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("알림을 찾을 수 없습니다. id: " + id));
            notificationRepository.delete(notification);
        }
    }

    /**
     * 클라이언트에 SSE 이벤트 전송 메서드
     * @param emitter SseEmitter 객체
     * @param id 이벤트 식별자
     * @param data 전송 데이터
     */
    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            log.info("이벤트 전송: {}", data);
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            log.error("SSE 연결 오류 발생", exception);
        }
    }
}