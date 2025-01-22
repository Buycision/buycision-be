package project.gatewayservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.gatewayservice.auth.controller.dto.request.LoginRequest;
import project.gatewayservice.auth.controller.dto.response.LoginResponse;
import project.gatewayservice.auth.domain.OAuthType;
import project.gatewayservice.auth.infra.OAuth2Provider;
import project.gatewayservice.auth.infra.OAuth2Providers;
import project.gatewayservice.auth.infra.OAuth2UserResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;
    private final JwtProvider jwtProvider;
    private final OAuth2Providers oAuth2Providers;

    public Mono<LoginResponse> login(LoginRequest loginRequest, String authType) {
        OAuthType oauthType = OAuthType.from(authType);
        OAuth2Provider oAuth2Provider = oAuth2Providers.getProvider(oauthType);
        OAuth2UserResponse oAuth2MemberResponse = oAuth2Provider.getUserResponse(loginRequest.accessToken());

        return userClient.findByOAuthTypeAndEmail(oauthType.name(), oAuth2MemberResponse.email())
                .switchIfEmpty(
                        userClient.registerUser(oAuth2MemberResponse.toUser())
                )
                .flatMap(userDto -> {
                    String accessToken = jwtProvider.createToken(userDto.id().toString());

                    return Mono.just(
                            LoginResponse.from(userDto.id().toString(), userDto.nickname(), accessToken)
                    );
                });
    }
}
