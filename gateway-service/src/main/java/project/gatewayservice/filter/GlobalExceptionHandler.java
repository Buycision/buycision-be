package project.gatewayservice.filter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("isSuccess", false);
        errorResponse.put("message", "예외가 발생했습니다.");

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
    }

    // 404 Not Found 예외 처리
    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleNotFound(ResponseStatusException ex) {
        if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("isSuccess", false);
            errorResponse.put("message", "요청한 URL을 찾을 수 없습니다.");
            errorResponse.put("error", "404 Not Found");

            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse));
        }
        return Mono.error(ex);
    }
}