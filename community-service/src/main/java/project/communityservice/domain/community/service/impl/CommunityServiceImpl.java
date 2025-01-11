package project.communityservice.domain.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.community.dto.CommunityResponse;
import project.communityservice.domain.community.service.CommunityService;
import project.communityservice.domain.board.repository.CommunityRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;

    @Override
    public List<CommunityResponse> getCommunityList() {
        return List.of();
    }

    @Override
    public CommunityResponse getCommunityById(Long id) {
        return null;
    }

    @Override
    public CommunityResponse addCommunity(String name, String description) {
        return null;
    }

    @Override
    public CommunityResponse updateCommunity(String name, String description) {
        return null;
    }

    @Override
    public void deleteCommunityById(Long id) {

    }
}
