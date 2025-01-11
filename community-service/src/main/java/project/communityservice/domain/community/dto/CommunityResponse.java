package project.communityservice.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.communityservice.domain.community.entity.Community;

@Getter
@AllArgsConstructor
public class CommunityResponse {
    Community community;
}
