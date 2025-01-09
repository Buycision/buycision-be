package project.gatewayservice.auth.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import project.gatewayservice.auth.service.dto.UserDto;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public record UserPrincipal(UserDto user, Map<String, Object> attributes) implements OAuth2User {

    public static UserPrincipal from(UserDto user, Map<String, Object> attributes) {
        return new UserPrincipal(user, attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_USER");
    }

    @Override
    public String getName() {
        return "testName";
    }
}
