package project.communityservice.domain.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.community.dto.response.CommunityResponse;
import project.communityservice.domain.community.entity.Community;
import project.communityservice.domain.community.repository.CommunityRepository;
import project.communityservice.domain.community.service.CommunityService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;

    /**
     * 전체 커뮤니티 가져오기
     * @param pageable
     * @return
     */
    @Override
    public List<CommunityResponse> getCommunityList(Pageable pageable) {
        Page<Community> communityPage = communityRepository.findAll(pageable);
        List<Community> community = communityPage.getContent();

        return CommunityResponse.listOf(community);
    }

    /**
     * 해당 커뮤니티 상세 내용 가져오기
     * @param id
     * @return
     */
    @Override
    public CommunityResponse getCommunity(Long id) {
        Community community = communityRepository.getByIdOrThrow(id);

        return CommunityResponse.of(community);
    }

    /**
     * 커뮤니티 생성
     * @param name
     * @param description
     * @return
     */
    @Override
    @Transactional
    public CommunityResponse createCommunity(String name, String description) {
        Community community = Community.createFrom(name, description);

        communityRepository.save(community);

        return CommunityResponse.of(community);
    }

    /**
     * 커뮤니티 수정
     * @param id
     * @param name
     * @param description
     * @return
     */
    @Override
    @Transactional
    public CommunityResponse updateCommunity(Long id, String name, String description) {
        Community community = communityRepository.getByIdOrThrow(id);

        Community newCommunity = Community.updateFrom(community.getId(), name, description);

        communityRepository.save(newCommunity);

        return CommunityResponse.of(newCommunity);
    }


    /**
     * 커뮤니티 삭제
     * @param id
     */
    @Override
    @Transactional
    public void deleteCommunityById(Long id) {
        Community community = communityRepository.getByIdOrThrow(id);
        communityRepository.delete(community);
    }

    /**
     * 본인이 만든 커뮤니티 가져오기
     * @param host
     * @param pageable
     * @return
     */
    @Override
    public List<CommunityResponse> getCommunityByHost(Long host, Pageable pageable) {
        Page<Community> communityPage = communityRepository.findAllByHost(host, pageable);
        List<Community> community = communityPage.getContent();
        
        return CommunityResponse.listOf(community);
    }

    /**
     * 본인이 참여하는 커뮤니티 가져오기
     * @param participants
     * @param pageable
     * @return
     */
//    @Override
//    public List<CommunityResponse> getCommunityById(Long participants, Pageable pageable) {
//        Page<Community> communityPage = communityRepository.findAllByParticipants(participants, pageable);
//        List<Community> community = communityPage.getContent();
//
//        return CommunityResponse.listOf(community);
//    }


}