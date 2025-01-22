package project.userservice.domain.dto.response;

import project.userservice.domain.entity.User;

public record UserIdResponse(
        Long id
) {
    public static UserIdResponse of(Long id) {
        return new UserIdResponse(id);
    }
}
