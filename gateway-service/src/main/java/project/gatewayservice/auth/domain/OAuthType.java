package project.gatewayservice.auth.domain;

import project.gatewayservice.auth.exception.AuthExceptionType;
import project.globalservice.exception.BaseException;

import java.util.Arrays;

public enum OAuthType {

    GOOGLE,
    KAKAO,
    ADMIN,
    ;

    public static OAuthType from(String other) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(other))
                .findFirst()
                .orElseThrow(() -> new BaseException(AuthExceptionType.OAUTH_TYPE_NOT_VALID));
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
