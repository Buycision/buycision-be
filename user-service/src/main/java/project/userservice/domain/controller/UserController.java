package project.userservice.domain.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.userservice.domain.dto.request.UserSignUpRequest;
import project.userservice.domain.dto.request.UserUpdateRequest;
import project.userservice.domain.dto.response.UserIdResponse;
import project.userservice.domain.dto.response.UserInfoResponse;
import project.userservice.domain.entity.User;
import project.userservice.domain.repository.UserRepository;
import project.userservice.domain.service.UserService;
import project.userservice.resolver.Auth;
import project.userservice.resolver.AuthUser;

import java.util.List;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 정보 조회", description = "현재 로그인한 사용자의 정보를 반환합니다.")
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @Parameter(hidden = true) @Auth AuthUser authUser) {
        return ResponseEntity.ok(userService.getUserInfo(authUser.userId()));
    }

    @Operation(summary = "사용자 닉네임 수정", description = "사용자의 닉네임을 변경합니다.")
    @PostMapping("/nickname")
    public ResponseEntity<UserInfoResponse> updateUserNickname(
            @Auth AuthUser authUser,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUserNickname(authUser.userId(), request));
    }

    @Operation(summary = "이메일로 정보 조회", description = "이메일로 사용자 정보를 조회합니다.")
    @GetMapping("/email/{email}")
    public UserInfoResponse getUserInfoByEmail(@PathVariable String email) {
        return userService.getUserInfoByEmail(email);
    }

    @Operation(summary = "회원가입")
    @PostMapping("/register")
    public ResponseEntity<UserInfoResponse> registerUser(@Valid @RequestBody UserSignUpRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @Operation(summary = "ID로 유저 조회(Feign)")
    @GetMapping("/id/{id}")
    public UserIdResponse getUserId(@PathVariable("id") Long id) {
        return userService.getId(id);
    }

    @Operation(summary = "유저 유효성 검증(Feign)")
    @GetMapping("/validate/{id}")
    public boolean isUserValid(@PathVariable("id") Long id) {
        return userService.isUserValid(id);
    }

    // test
    @Hidden
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        log.info("Hello World");
        return ResponseEntity.ok("Hello World");
    }

    private final UserRepository userRepository;

    @Hidden
    @GetMapping("/all")
    public ResponseEntity<List<User>> all() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}