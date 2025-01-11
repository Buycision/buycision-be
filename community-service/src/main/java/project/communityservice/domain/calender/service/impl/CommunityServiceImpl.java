package project.communityservice.domain.calender.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.repository.CommunityRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl {
    private final CommunityRepository communityRepository;

}
