package project.communityservice.domain.board.post.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.communityservice.global.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public class BoardException extends RuntimeException {
    private final ErrorCode errorCode;
}
