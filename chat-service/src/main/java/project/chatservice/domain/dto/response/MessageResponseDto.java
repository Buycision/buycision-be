package project.chatservice.domain.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

// 클라이언트가 메세지 목록을 조회할 때 반환되는 응답 데이터
@Builder
public record MessageResponseDto(
        String messageId,
        String content,
        Long userId,
        LocalDateTime createAt
) {}
