package project.userservice.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.userservice.domain.dto.request.UserSignUpRequest;
import project.userservice.domain.dto.response.UserInfoResponse;
import project.userservice.domain.dto.response.UserSignUpResponse;
import project.userservice.domain.dto.response.UserWithdrawResponse;
import project.userservice.domain.service.UserService;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/register")
    public ResponseEntity<UserSignUpResponse> register(@RequestBody @Valid UserSignUpRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @Operation(summary = "사용자 정보 조회", description = "현재 로그인한 사용자의 정보를 반환합니다.")
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo() {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "사용자 닉네임 수정", description = "사용자의 닉네임을 변경합니다.")
    @PostMapping("/nickname")
    public ResponseEntity<Void> updateUserNickname() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "비밀번호 변경", description = "사용자의 비밀번호를 변경합니다.")
    @PostMapping("/renew-password")
    public ResponseEntity<Void> updateUserPassword() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원탈퇴", description = "사용자의 계정을 삭제 처리합니다.")
    @PostMapping("/withdraw")
    public ResponseEntity<UserWithdrawResponse> withdrawUser() {
        return ResponseEntity.ok(null);
    }
}