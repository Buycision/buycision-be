package project.userservice.domain.repository;

import project.globalservice.exception.BaseException;
import org.springframework.data.jpa.repository.JpaRepository;
import project.userservice.domain.entity.User;
import project.userservice.domain.exception.UserExceptionType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default User getUserById(Long id) {
        return findById(id).orElseThrow(() -> new BaseException(UserExceptionType.USER_NOT_FOUND));
    }

    Optional<User> findByEmail(String email);
}
