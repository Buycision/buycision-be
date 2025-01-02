package project.userservice.domain.service;

import project.userservice.domain.dto.request.UserUpdateRequest;
import project.userservice.domain.dto.response.UserInfoResponse;

public interface UserService {

    UserInfoResponse getUserInfo(Long userId);

    UserInfoResponse updateUserNickname(Long userId, UserUpdateRequest request);

}
