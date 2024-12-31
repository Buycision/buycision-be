package project.userservice.domain.entity;

import project.globalservice.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import project.userservice.domain.exception.UserExceptionType;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Authority {

    ROLE_GUEST, // 비회원
    ROLE_USER,  // 회원
    ROLE_ADMIN; // 관리자

    @JsonCreator
    public static Authority from(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_AUTHORITY_NOT_VALID));
    }
}

