package project.communityservice.domain.community.service;

import project.communityservice.domain.community.dto.CommunityResponse;

import java.util.List;

public interface CommunityService {
    // 목록 조회
    List<CommunityResponse> getCommunityList();

    // 단건 조회
    CommunityResponse getCommunityById(Long id);

    // 생성
    CommunityResponse addCommunity(String name, String description);

    // 수정
    CommunityResponse updateCommunity(String name, String description);

    // 삭제
    void deleteCommunityById(Long id);
}
