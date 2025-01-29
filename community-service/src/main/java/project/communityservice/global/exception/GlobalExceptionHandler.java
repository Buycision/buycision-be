package project.communityservice.global.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.communityservice.domain.board.post.exception.BoardException;
import project.communityservice.global.exception.dto.ErrorResponseV0;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BoardException.class)
    public ErrorResponseV0 boardException(BoardException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return getErrorResponse(errorCode);
    }

    private ErrorResponseV0 getErrorResponse(ErrorCode errorCode) {
        return ErrorResponseV0.builder()
                .name(errorCode.name())
                .errorCode(errorCode.getErrorCode())
                .message(errorCode.getMessage())
                .build();
    }
}
