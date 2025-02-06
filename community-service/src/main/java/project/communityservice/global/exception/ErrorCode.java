package project.communityservice.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 게시글 존재 x
    NOT_FOUND_BOARD(00, "해당 게시글은 존재하지 않습니다.");

    private final int errorCode;
    private final String message;
}
