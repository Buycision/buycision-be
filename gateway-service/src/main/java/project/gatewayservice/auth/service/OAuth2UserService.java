package project.gatewayservice.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.gatewayservice.auth.domain.OAuth2UserInfo;
import project.gatewayservice.auth.domain.OAuth2UserInfoFactory;
import project.gatewayservice.auth.domain.UserPrincipal;
import project.gatewayservice.auth.service.dto.UserDto;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService implements ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserClient userClient;

    @Override
    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return new DefaultReactiveOAuth2UserService().loadUser(userRequest)
                .flatMap(oAuth2User -> {
                    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                            userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

                    if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
                        return Mono.error(new OAuth2AuthenticationException("Email not found from OAuth2 provider"));
                    }

                    return fetchOrRegisterUser(oAuth2UserInfo)
                            .map(user -> UserPrincipal.from(user, oAuth2User.getAttributes()));
                });
    }

    private Mono<UserDto> fetchOrRegisterUser(OAuth2UserInfo oAuth2UserInfo) {
        return userClient.findByEmail(oAuth2UserInfo.getEmail())
                .switchIfEmpty(registerUser(oAuth2UserInfo));
    }

    private Mono<UserDto> registerUser(OAuth2UserInfo oAuth2UserInfo) {
        return userClient.registerUser(
                UserDto.builder()
                        .email(oAuth2UserInfo.getEmail())
                        .nickname(oAuth2UserInfo.getName())
                        .profileImageUrl(oAuth2UserInfo.getImageUrl())
                        .build());
    }
}
