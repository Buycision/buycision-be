package project.notificationservice.domain.dto.request;

public record NotificationDeleteRequest(
        Long[] idList
) {
}
