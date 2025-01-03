package project.userservice.domain.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.userservice.domain.dto.request.UserUpdateRequest;
import project.userservice.domain.dto.response.UserInfoResponse;
import project.userservice.domain.service.UserService;
import project.userservice.resolver.Auth;
import project.userservice.resolver.AuthUser;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 정보 조회", description = "현재 로그인한 사용자의 정보를 반환합니다.")
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@Parameter(hidden = true) @Auth AuthUser authUser) {
        return ResponseEntity.ok(userService.getUserInfo(authUser.userId()));
    }

    @Operation(summary = "사용자 닉네임 수정", description = "사용자의 닉네임을 변경합니다.")
    @PostMapping("/nickname")
    public ResponseEntity<UserInfoResponse> updateUserNickname(
            @Parameter(hidden = true) @Auth AuthUser authUser,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUserNickname(authUser.userId(), request));
    }

    // test
    @Hidden
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        log.info("Hello World");
        return ResponseEntity.ok("Hello World");
    }
}