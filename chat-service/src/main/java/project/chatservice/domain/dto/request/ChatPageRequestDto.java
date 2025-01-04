package project.chatservice.domain.dto.request;

// 채팅 메세지를 페이징 처리하여 요청할 때 사용
public record ChatPageRequestDto(
        int size,
        int page
) {}
