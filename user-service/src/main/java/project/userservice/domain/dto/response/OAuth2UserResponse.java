package project.userservice.domain.dto.response;

import project.userservice.domain.entity.User;

public interface OAuth2UserResponse {

    String getEmail();

    User toUser();
}
