package project.notificationservice.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import project.notificationservice.domain.dto.request.NotificationDeleteRequest;
import project.notificationservice.domain.dto.response.NotificationResponse;
import project.notificationservice.domain.dto.response.StatusResponse;
import project.notificationservice.domain.service.NotificationService;
import project.notificationservice.global.resolver.Auth;
import project.notificationservice.global.resolver.AuthUser;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "SSE 연결", description = "로그인 한 유저가 SSE 연결을 할 수 있다.")
    public ResponseEntity<SseEmitter> connect(
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
            @Parameter(hidden = true) @Auth AuthUser authUser) {

        Long userId = authUser.userId();

        SseEmitter emitter = notificationService.subscribe(lastEventId, userId);

        return ResponseEntity.ok(emitter);
    }

    @GetMapping("/all")
    @Operation(summary = "모든 알림 조회", description = "로그인 한 유저의 모든 알림을 조회한다.")
    public ResponseEntity<List<NotificationResponse>> notifications(
            @Parameter(hidden = true) @Auth AuthUser authUser
    ) {
        Long userId = authUser.userId();
        return ResponseEntity.ok(notificationService.findAllById(userId));
    }

    @PostMapping("/checked/{id}")
    @Operation(summary = "알림 읽음", description = "알림 읽음 상태만 변경할 수 있다.")
    public ResponseEntity<StatusResponse> readNotification(
            @Parameter(hidden = true) @Auth AuthUser authUser
    ) {
        Long userId = authUser.userId();
        notificationService.readNotifications(userId);
        return ResponseEntity.ok(StatusResponse.success());
    }

    @DeleteMapping
    @Operation(summary = "알림 삭제")
    public ResponseEntity<StatusResponse> deleteNotification(@RequestBody NotificationDeleteRequest request) {
        notificationService.deleteNotification(request.idList());
        return ResponseEntity.ok(StatusResponse.success());
    }
}
