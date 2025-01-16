package project.communityservice.domain.community.dto.response;

import project.communityservice.domain.community.entity.Community;

public record CommunityResponse (
        Long id,
        String name,
        String description
){

    public static CommunityResponse of (Community community) {
        return new CommunityResponse(community.getId(), community.getName(), community.getDescription());
    }
}
