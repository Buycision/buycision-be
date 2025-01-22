package project.gatewayservice.auth.infra;

import project.gatewayservice.auth.service.dto.UserDto;

public interface OAuth2UserResponse {

    String email();

    UserDto toUser();
}
