package project.communityservice.domain.community.service;

import project.communityservice.domain.community.dto.response.CommunityResponse;
import project.communityservice.domain.community.dto.response.CommunityResponses;

import java.util.List;

public interface CommunityService {
    // 목록 조회
    List<CommunityResponses> getCommunityList();

    // 단건 조회
    CommunityResponse getCommunity(Long id);

    // 생성
    CommunityResponse createCommunity(String name, String description);

    // 수정
    CommunityResponse updateCommunity(Long id, String name, String description);

    // 삭제
    void deleteCommunityById(Long id);
}
