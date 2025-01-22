package project.gatewayservice.auth.infra.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import project.gatewayservice.auth.domain.OAuthType;
import project.gatewayservice.auth.exception.AuthExceptionType;
import project.gatewayservice.auth.infra.OAuth2Provider;
import project.globalservice.exception.BaseException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2Provider implements OAuth2Provider {

    private final KakaoConfig kakaoConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public KakaoOAuth2UserResponse getUserResponse(String accessToken) {
        ResponseEntity<KakaoUserResponse> kakaoUserResponse = getMemberResource(accessToken);
        return new KakaoOAuth2UserResponse(Objects.requireNonNull(kakaoUserResponse.getBody()).getEmail(),
                kakaoUserResponse.getBody().getNickname(),
                kakaoUserResponse.getBody().getProfileImageUrl());
    }

    private ResponseEntity<KakaoUserResponse> getMemberResource(String accessToken) {
        String resourceUri = kakaoConfig.getResourceUri();

        HttpHeaders headers = createRequestHeader();
        headers.setBearerAuth(accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(
                    resourceUri,
                    HttpMethod.GET,
                    request,
                    KakaoUserResponse.class
            );
        } catch (HttpClientErrorException e) {
            throw new BaseException(AuthExceptionType.USER_RESOURCES_ERROR);
        }
    }

    private HttpHeaders createRequestHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return header;
    }

    @Override
    public OAuthType getOauthType() {
        return OAuthType.KAKAO;
    }
}
