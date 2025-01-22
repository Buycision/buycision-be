package project.chatservice.domain.dto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.chatservice.domain.dto.response.ChatUserResponseDto;
import project.chatservice.domain.dto.response.UserResponseDto;

@FeignClient(name = "user-service", url = "http://user-service:8080")
public interface UserFeignClient {

    @GetMapping("/users/{id}")
    UserResponseDto getUserById(@PathVariable("id") Long id);

    @GetMapping("/users/validate/{id}")
    boolean isUserValid(@PathVariable("id") Long id);
}
