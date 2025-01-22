package project.chatservice.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.globalservice.exception.ExceptionType;
import project.globalservice.exception.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatExceptionType implements ExceptionType {

    CHAT_NOT_FOUND(HttpStatus.NOT_FOUND, "chat-001", "채팅방을 찾을 수 없습니다."),
    CHAT_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "chat-002", "채팅방에 참여한 사용자를 찾을 수 없습니다."),
    INVALID_CHAT_USER(HttpStatus.BAD_REQUEST, "chat-003", "유효한 사용자가 아닙니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
