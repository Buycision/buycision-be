package project.globalservice.exception;

public interface ExceptionType {

    HttpStatus getHttpStatus();
    String getErrorCode();
    String getMessage();
}
