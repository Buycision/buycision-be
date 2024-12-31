package project.globalservice.response;

import project.globalservice.exception.ExceptionType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "timestamp"})
public class ExceptionResponse {

    private final boolean isSuccess;
    private final String code;
    private final String message;
    private final LocalDateTime timestamp;

    public ExceptionResponse(String code, String message) {
        this.isSuccess = false;
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ExceptionResponse(ExceptionType exceptionType) {
        this(exceptionType.getErrorCode(), exceptionType.getMessage());
    }

    public ExceptionResponse(ExceptionType exceptionType, String message) {
        this(exceptionType.getErrorCode(), message);
    }
}
