package project.communityservice.domain.calender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.service.impl.CommunityServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calenders")
public class CalenderController {
    private final CalenderService calenderService;

    // 일정 조회
    @GetMapping
    public String

    // 일정 등록

    // 일정 수정

    // 일정 삭제
}
