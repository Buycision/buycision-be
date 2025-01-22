package project.communityservice.domain.community.dto.response;

import project.communityservice.domain.community.entity.Community;

import java.util.List;
import java.util.stream.Collectors;

public record CommunityResponse (
        Long id,
        String name,
        String description
){

    public static CommunityResponse of (Community community) {
        return new CommunityResponse(community.getId(), community.getName(), community.getDescription());
    }

    public static List<CommunityResponse> listOf(List<Community> community) {
        return community.stream()
                .map(CommunityResponse::of)
                .collect(Collectors.toList());
    }
}
