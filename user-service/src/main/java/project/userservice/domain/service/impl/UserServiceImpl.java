package project.userservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.userservice.domain.dto.request.UserRenewPasswordRequest;
import project.userservice.domain.dto.request.UserSignUpRequest;
import project.userservice.domain.dto.request.UserUpdateRequest;
import project.userservice.domain.dto.response.UserInfoResponse;
import project.userservice.domain.dto.response.UserSignUpResponse;
import project.userservice.domain.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserSignUpResponse register(UserSignUpRequest request) {
        return null;
    }

    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        return null;
    }

    @Override
    public void updateUserNickname(Long userId, UserUpdateRequest request) {

    }

    @Override
    public void updateUserPassword(Long userId, UserRenewPasswordRequest request) {

    }

    @Override
    public void withdrawUser(Long userId) {

    }
}
