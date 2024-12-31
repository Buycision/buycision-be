package project.userservice.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import project.userservice.domain.entity.Authority;
import project.userservice.domain.entity.User;

public record UserSignUpRequest(
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 입력해주세요.")
        String password,

        @NotBlank(message = "전화번호는 필수 입력값입니다.")
        @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 10~11자리의 숫자만 입력 가능합니다.")
        String phoneNumber,

        @NotBlank(message = "닉네임은 필수 입력값입니다.")
        @Size(max = 30, message = "닉네임은 최대 30자까지 입력 가능합니다.")
        String nickname,

        @Size(max = 255, message = "프로필 이미지 URL은 255자를 초과할 수 없습니다.")
        String profileImageUrl
) {
    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .reliability(0.0)
                .authority(Authority.ROLE_USER)
                .build();
    }
}
