package project.communityservice.domain.calender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.calender.entity.Calender;
import project.communityservice.domain.calender.service.CalenderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calenders")
public class CalenderController {
    private final CalenderService calenderService;

    // 일정 조회
    @GetMapping("/{calenderId}")
    public String calenders(@PathVariable long calenderId) {
        return "일정 조회입니다.";
    }

    // 일정 등록
    @PostMapping
    public String addCalender(@RequestBody Calender calender) {
        return "일정 등록입니다.";
    }

    // 일정 수정
    @PatchMapping
    public String updateCalender(@RequestBody Calender calender) {
        return "일정 수정입니다.";
    }

    // 일정 삭제
    @DeleteMapping("/{calenderId}")
    public String deleteCalender(@PathVariable long calenderId) {
        return "일정 삭제입니다. ";
    }
}
