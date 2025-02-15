package project.notificationservice.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class EmitterRepository {

    /**
     * Map에 회원과 연결된 SSE SseEmitter 객체를 저장, 동시성 보장
     */
    public final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    /**
     * Event를 캐시에 저장
     */
    public final Map<String, Object> cache = new ConcurrentHashMap<>();

    /**
     * id, sseEmitter map에 저장
     */
    public SseEmitter save(String id, SseEmitter sseEmitter) {
        emitters.put(id, sseEmitter);
        return sseEmitter;
    }

    /**
     * id로 시작하는 키를 가진 eventcache 맵 항목을 필터링하여 맵으로 변환
     */
    public Map<String, Object> findAllCacheStartWithId(String id) {
        return cache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(id))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteById(String id) {
        emitters.remove(id);
        cache.remove(id);
    }

    public void saveCache(String id, Object data) {
        cache.put(id, data);
    }
}
