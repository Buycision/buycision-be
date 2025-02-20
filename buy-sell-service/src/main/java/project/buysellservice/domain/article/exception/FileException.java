package project.buysellservice.domain.article.exception;


import lombok.RequiredArgsConstructor;
import project.buysellservice.global.exception.ErrorCode;

@RequiredArgsConstructor
public class FileException extends RuntimeException {
    private final ErrorCode errorCode;
}
