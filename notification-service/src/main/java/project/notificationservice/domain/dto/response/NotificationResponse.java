package project.notificationservice.domain.dto.response;

import lombok.Builder;
import project.notificationservice.domain.entity.Notification;

@Builder
public record NotificationResponse(
        Long id,
        Integer senderNo,
        String senderName,
        Integer receiverNo,
        String type,
        String url,
        String content,
        Boolean isRead,
        Boolean isDel
) {
    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .senderNo(notification.getSenderNo())
                .senderName(notification.getSenderName())
                .receiverNo(notification.getSenderNo())
                .type(notification.getTypeEnum().name())
                .url(notification.getUrl())
                .content(notification.getContent())
                .isRead(notification.getIsRead())
                .isDel(notification.getIsDel())
                .build();
    }
}
