package project.gatewayservice.filter.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class GlobalPathConstants {

    public static final Set<String> AUTH_WHITELIST = Set.of(
            "/login",
            "/v3/api-docs",
            "/user/v3/api-docs",
            "/chat/v3/api-docs",
            "/community/v3/api-docs",
            "/user/email",
            "/user/register"
    );
}
