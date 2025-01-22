package project.chatservice.domain.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.chatservice.domain.dto.UserDto;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/id/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

    @GetMapping("/validate/{id}")
    boolean isUserValid(@PathVariable("id") Long id);
}
