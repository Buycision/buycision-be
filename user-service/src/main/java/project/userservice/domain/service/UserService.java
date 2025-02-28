package project.userservice.domain.service;

import project.userservice.domain.dto.request.UserSignUpRequest;
import project.userservice.domain.dto.request.UserUpdateRequest;
import project.userservice.domain.dto.response.UserIdResponse;
import project.userservice.domain.dto.response.UserInfoResponse;

public interface UserService {

    UserInfoResponse getUserInfo(Long userId);

    UserInfoResponse getUserInfoByEmail(String email);

    UserInfoResponse updateUserNickname(Long userId, UserUpdateRequest request);

    UserInfoResponse getUserInfoByOAuthTypeAndEmail(String oauthType, String email);

    UserInfoResponse registerUser(UserSignUpRequest request);

    UserIdResponse getId(Long id);

    boolean isUserValid(Long id);
}
