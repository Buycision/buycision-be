package project.communityservice.domain.calender.service;

import project.communityservice.domain.calender.dto.CalenderRequest;
import project.communityservice.domain.calender.dto.CalenderResponse;

public interface CommunityService {
    // 일정 조회
    CalenderResponse getCalender(Long calenderId);

    // 일정 등록
    CalenderResponse addCalender(CalenderRequest calenderRequest);

    // 일정 수정
    CalenderResponse updateCalender(CalenderRequest calenderRequest);

    // 일정 삭제
    void deleteCalender(Long calenderId);

}
