package project.communityservice.domain.calender.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.calender.dto.CalenderRequest;
import project.communityservice.domain.calender.dto.CalenderResponse;
import project.communityservice.domain.calender.repository.CalenderRepository;
import project.communityservice.domain.calender.service.CommunityService;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CalenderRepository calenderRepository;

    @Override
    public CalenderResponse getCalender(Long calenderId) {
        return null;
    }

    @Override
    public CalenderResponse addCalender(CalenderRequest calenderRequest) {
        return null;
    }

    @Override
    public CalenderResponse updateCalender(CalenderRequest calenderRequest) {
        return null;
    }

    @Override
    public void deleteCalender(Long calenderId) {

    }
}
