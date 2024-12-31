package project.userservice.domain.entity;

import project.globalservice.exception.BaseException;
import project.userservice.domain.exception.UserExceptionType;

import java.util.Arrays;

/**
 * 사용중인 OAuth 타입
 * GOOGLE, KAKAO
 */
public enum OAuthType {

    GOOGLE,
    KAKAO,
    NAVER,
    APPLE,
    ADMIN,
    ;

    public static OAuthType from(String other) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(other))
                .findFirst()
                .orElseThrow(() -> new BaseException(UserExceptionType.OAUTH_TYPE_NOT_VALID));
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
