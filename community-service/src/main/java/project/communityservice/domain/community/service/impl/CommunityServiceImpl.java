package project.communityservice.domain.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.community.dto.response.CommunityResponse;
import project.communityservice.domain.community.dto.response.CommunityResponses;
import project.communityservice.domain.community.entity.Community;
import project.communityservice.domain.community.repository.CommunityRepository;
import project.communityservice.domain.community.service.CommunityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;

    @Override
    public List<CommunityResponses> getCommunityList() {
        List<Community> communities = communityRepository.findAll();

        return communities.stream()
                .map(CommunityResponses::listOf)
                .collect(Collectors.toList());
    }

    @Override
    public CommunityResponse getCommunity(Long id) {
        Community community = communityRepository.getByIdOrThrow(id);

        return CommunityResponse.of(community);
    }

    @Override
    @Transactional
    public CommunityResponse createCommunity(String name, String description) {
        Community community = Community.createFrom(name, description);

        communityRepository.save(community);

        return CommunityResponse.of(community);
    }

    @Override
    @Transactional
    public CommunityResponse updateCommunity(Long id, String name, String description) {
        Community community = communityRepository.getByIdOrThrow(id);

        Community newCommunity = Community.updateFrom(community.getId(), name, description);

        communityRepository.save(newCommunity);

        return CommunityResponse.of(newCommunity);
    }

    @Override
    @Transactional
    public void deleteCommunityById(Long id) {
        Community community = communityRepository.getByIdOrThrow(id);
        communityRepository.delete(community);
    }
}