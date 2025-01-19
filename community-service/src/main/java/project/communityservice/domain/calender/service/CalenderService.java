package project.communityservice.domain.calender.service;

import project.communityservice.domain.calender.dto.response.CalenderResponse;
import project.communityservice.domain.calender.dto.request.CalenderRequest;

import java.util.List;

public interface CalenderService {
    List<CalenderResponse> listCalender();

    // 일정 조회
    CalenderResponse getCalender(Long id);

    // 일정 등록
    CalenderResponse createCalender();

    // 일정 수정
    CalenderResponse modifyCalender(CalenderRequest calenderRequest);

    // 일정 삭제
    void deleteCalender(Long calenderId);

}
