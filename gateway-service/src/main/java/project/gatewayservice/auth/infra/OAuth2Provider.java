package project.gatewayservice.auth.infra;

import project.gatewayservice.auth.domain.OAuthType;

public interface OAuth2Provider {

    OAuth2UserResponse getUserResponse(String authCode);

    OAuthType getOauthType();

}