package project.userservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.userservice.domain.dto.request.UserUpdateRequest;
import project.userservice.domain.dto.response.UserInfoResponse;
import project.userservice.domain.entity.User;
import project.userservice.domain.repository.UserRepository;
import project.userservice.domain.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.getUserById(userId);

        return UserInfoResponse.from(user);
    }

    @Override
    public UserInfoResponse updateUserNickname(Long userId, UserUpdateRequest request) {
        User user = userRepository.getUserById(userId);

        user.updateNickname(request.nickname());

        return UserInfoResponse.from(user);
    }
}
