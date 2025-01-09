package project.gatewayservice.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.globalservice.exception.ExceptionType;
import project.globalservice.exception.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthExceptionType implements ExceptionType {

    OAUTH_TYPE_NOT_VALID(HttpStatus.BAD_REQUEST, "auth-001", "존재하지 않는 인증 타입입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
