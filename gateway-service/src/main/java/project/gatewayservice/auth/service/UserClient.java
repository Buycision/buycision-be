package project.gatewayservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import project.gatewayservice.auth.service.dto.UserDto;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final WebClient webClient;

    public Mono<UserDto> findByOAuthTypeAndEmail(String oauthType, String email) {
        return webClient.get()
                .uri("/user/oauth/{oauthType}/email/{email}", oauthType, email)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDto.class)
                .onErrorResume(e -> Mono.empty());
    }

    public Mono<UserDto> findByEmail(String email) {
        return webClient.get()
                .uri("/user/email/{email}", email)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserDto.class)
                .onErrorResume(e -> Mono.empty());
    }

    public Mono<UserDto> registerUser(UserDto userDto) {
        return webClient.post()
                .uri("/user/register")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(UserDto.class);
    }
}
