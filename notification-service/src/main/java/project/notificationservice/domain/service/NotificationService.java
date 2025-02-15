package project.notificationservice.domain.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import project.notificationservice.domain.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {


    SseEmitter subscribe(String lastEventId, Long userId);

    List<NotificationResponse> findAllById(Long userId);

    void readNotifications(Long userId);

    void deleteNotification(Long[] idList);
}
