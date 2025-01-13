package project.gatewayservice.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.globalservice.exception.ExceptionType;
import project.globalservice.exception.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthExceptionType implements ExceptionType {

    OAUTH_TYPE_NOT_VALID(HttpStatus.BAD_REQUEST, "auth-001", "존재하지 않는 인증 타입입니다"),
    INVALID_TOKEN_SIGNATURE(HttpStatus.UNAUTHORIZED, "auth-002", "유효하지 않은 토큰 서명입니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "auth-003", "만료된 토큰입니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "auth-004", "유효하지 않은 토큰입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
