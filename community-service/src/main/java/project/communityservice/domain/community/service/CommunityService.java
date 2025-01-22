package project.communityservice.domain.community.service;

import org.springframework.data.domain.Pageable;
import project.communityservice.domain.community.dto.response.CommunityResponse;

import java.util.List;

public interface CommunityService {
    // 목록 조회
    List<CommunityResponse> getCommunityList(Pageable pageable);

    // 단건 조회
    CommunityResponse getCommunity(Long id);

    // 생성
    CommunityResponse createCommunity(String name, String description);

    // 수정
    CommunityResponse updateCommunity(Long id, String name, String description);

    // 삭제
    void deleteCommunityById(Long id);

    // 본인이 만든 커뮤니티 보이기
    List<CommunityResponse> getCommunityByHost(Long host, Pageable pageable);

    // 본인이 참가한 커뮤니티 보이기
    List<CommunityResponse> getCommunityById(Long participants, Pageable pageable);
}
