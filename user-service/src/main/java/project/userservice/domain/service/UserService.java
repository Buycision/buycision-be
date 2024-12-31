package project.userservice.domain.service;

import project.userservice.domain.dto.request.UserRenewPasswordRequest;
import project.userservice.domain.dto.request.UserSignUpRequest;
import project.userservice.domain.dto.request.UserUpdateRequest;
import project.userservice.domain.dto.response.UserInfoResponse;
import project.userservice.domain.dto.response.UserSignUpResponse;

public interface UserService {

    UserSignUpResponse register(UserSignUpRequest request);

    UserInfoResponse getUserInfo(Long userId);

    void updateUserNickname(Long userId, UserUpdateRequest request);

    void updateUserPassword(Long userId, UserRenewPasswordRequest request);

    void withdrawUser(Long userId);
}
