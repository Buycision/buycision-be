package project.gatewayservice.auth.infra;

import org.springframework.stereotype.Component;
import project.gatewayservice.auth.domain.OAuthType;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuth2Providers {

    private final Map<OAuthType, OAuth2Provider> oAuthProviderMap;

    public OAuth2Providers(Set<OAuth2Provider> oAuth2Providers) {
        oAuthProviderMap = oAuth2Providers.stream()
                .collect(Collectors.toMap(
                        OAuth2Provider::getOauthType,
                        Function.identity()
                ));
    }

    public OAuth2Provider getProvider(OAuthType oAuthType) {
        return oAuthProviderMap.get(oAuthType);
    }
}