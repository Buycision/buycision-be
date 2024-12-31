package project.userservice.domain.exception;

import project.globalservice.exception.ExceptionType;
import project.globalservice.exception.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthExceptionType implements ExceptionType {

    ILLEGAL_REGISTRATION_ID(HttpStatus.BAD_REQUEST, "AUTH-001", "존재하지 않는 인증 타입입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

}
