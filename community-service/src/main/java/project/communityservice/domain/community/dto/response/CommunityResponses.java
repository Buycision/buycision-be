package project.communityservice.domain.community.dto.response;

import project.communityservice.domain.community.entity.Community;

public record CommunityResponses(
        String name,
        String description
) {
    public static CommunityResponses listOf(Community community) {
        return new CommunityResponses(community.getName(), community.getDescription());
    }
}
