package project.communityservice.domain.calender.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.calender.dto.request.CalenderRequest;
import project.communityservice.domain.calender.dto.response.CalenderResponse;
import project.communityservice.domain.calender.entity.Calender;
import project.communityservice.domain.calender.repository.CalenderRepository;
import project.communityservice.domain.calender.service.CalenderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalenderServiceImpl implements CalenderService {
    private final CalenderRepository calenderRepository;


    @Override
    public List<CalenderResponse> listCalender() {
        List<Calender> calenders = calenderRepository.findAll();

        return CalenderResponse.listOf(calenders);
    }

    @Override
    public CalenderResponse getCalender(Long id) {
        Calender calender = calenderRepository.getByIdOrThorw(id);

        return CalenderResponse.of(calender);
    }

    @Override
    public CalenderResponse createCalender(String title, String content) {
        Calender calender = Calender.createFrom(title, content);

        calenderRepository.save(calender);

        return CalenderResponse.of(calender);
    }

    @Override
    public CalenderResponse modifyCalender(Long id, String title, String content) {
        Calender calender = calenderRepository.getByIdOrThorw(id);

        Calender newCalender = Calender.updateFrom(calender.getId(), title, content);

        calenderRepository.save(newCalender);

        return CalenderResponse.of(newCalender);
    }

    @Override
    public void deleteCalender(Long id) {
        Calender calender = calenderRepository.getByIdOrThorw(id);
        calenderRepository.delete(calender);
    }
}
