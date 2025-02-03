package project.gatewayservice.auth.infra.google;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GoogleOAuth2Provider implements OAuth2Provider {

    private final GoogleConfig googleConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public GoogleOAuth2UserResponse getUserResponse(String accessToken) {
        ResponseEntity<GoogleUserResponse> googleUserResponse = getUserResource(accessToken);
        return new GoogleOAuth2UserResponse(Objects.requireNonNull(googleUserResponse.getBody()).getEmail(),
                googleUserResponse.getBody().getName(),
                googleUserResponse.getBody().getPicture());
    }

    private ResponseEntity<GoogleUserResponse> getUserResource(String accessToken) {
        String resourceUri = googleConfig.getResourceUri();

        HttpHeaders headers = createRequestHeader();
        headers.setBearerAuth(accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(
                    resourceUri,
                    HttpMethod.GET,
                    request,
                    GoogleUserResponse.class
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
        return OAuthType.GOOGLE;
    }
}