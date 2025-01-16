package project.chatservice.domain.dto.response;

import lombok.Builder;

@Builder
public record ChatPageResponseDto(
        int size,
        int page,
        int totalPages,
        long totalElements
) {}
