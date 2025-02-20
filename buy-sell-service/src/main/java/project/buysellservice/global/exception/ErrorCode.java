package project.buysellservice.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 파일
    NOT_EXIST_BUCKET(00, "버킷이 존재하지 않습니다."),
    FAIL_UPLOAD_IMAGE(00, "이미지 업로드에 실패하였습니다.");





    private final int errorCode;
    private final String errorMsg;


}
