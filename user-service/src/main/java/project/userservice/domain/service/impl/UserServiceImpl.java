package project.userservice.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.globalservice.exception.BaseException;
import project.userservice.domain.dto.request.UserSignUpRequest;
import project.userservice.domain.dto.request.UserUpdateRequest;
import project.userservice.domain.dto.response.UserIdResponse;
import project.userservice.domain.dto.response.UserInfoResponse;
import project.userservice.domain.entity.OAuthType;
import project.userservice.domain.entity.User;
import project.userservice.domain.exception.UserExceptionType;
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
    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfoByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserInfoResponse::from)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public UserInfoResponse updateUserNickname(Long userId, UserUpdateRequest request) {
        User user = userRepository.getUserById(userId);

        user.updateNickname(request.nickname());

        return UserInfoResponse.from(user);
    }

    @Override
    public UserInfoResponse getUserInfoByOAuthTypeAndEmail(String oauthType, String email) {
        return userRepository.findByOauthTypeAndEmail(OAuthType.from(oauthType), email)
                .map(UserInfoResponse::from)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public UserInfoResponse registerUser(UserSignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BaseException(UserExceptionType.DUPLICATE_EMAIL);
        }

        User user = userRepository.save(request.toEntity());

        return UserInfoResponse.from(user);
    }

    @Override
    public UserIdResponse getId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));

        return UserIdResponse.of(user.getId());
    }

    @Override
    public boolean isUserValid(Long id) {
        return userRepository.existsById(id);
    }
}
