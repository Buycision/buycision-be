package project.chatservice.domain.dto.response;

import lombok.Builder;
import project.chatservice.domain.dto.request.MessageRequest;

import java.util.List;

@Builder
public record ChatResponseDto(
        ChatUserResponseDto user,
        ChatPageResponseDto pageableDto,
        List<MessageRequest> chatList
) {}
