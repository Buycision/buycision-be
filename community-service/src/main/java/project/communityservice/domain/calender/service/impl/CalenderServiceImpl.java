package project.communityservice.domain.calender.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.calender.repository.CalenderRepository;
import project.communityservice.domain.calender.service.CalenderService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalenderServiceImpl implements CalenderService {
    private final CalenderRepository calenderRepository;


    @Override
    public List<project.communityservice.domain.calender.dto.response.CalenderResponse> listCalender() {
        return List.of();
    }

    @Override
    public project.communityservice.domain.calender.dto.response.CalenderResponse getCalender(Long id) {
        return null;
    }

    @Override
    public project.communityservice.domain.calender.dto.response.CalenderResponse createCalender() {
        return null;
    }

    @Override
    public project.communityservice.domain.calender.dto.response.CalenderResponse modifyCalender(project.communityservice.domain.calender.dto.request.CalenderRequest calenderRequest) {
        return null;
    }

    @Override
    public void deleteCalender(Long calenderId) {

    }
}
