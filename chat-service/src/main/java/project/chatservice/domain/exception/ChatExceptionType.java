package project.chatservice.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.globalservice.exception.ExceptionType;
import project.globalservice.exception.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatExceptionType implements ExceptionType {

    NOT_FOUND_CHAT(HttpStatus.NOT_FOUND, "chat-001", "채팅을 찾을 수 없습니다."),

    NOT_FOUND_USER_CHAT_ROOM(HttpStatus.NOT_FOUND, "user-chat-room-001", "유저 채팅방을 찾을 수 없습니다."),

    NOT_FOUND_CHAT_ROOM(HttpStatus.NOT_FOUND, "chat-room-001", "채팅방을 찾을 수 없습니다."),
    ALREADY_DEACTIVATED_CHAT_ROOM(HttpStatus.BAD_REQUEST, "chat-room-002", "이미 비활성화된 채팅방입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
