package project.communityservice.domain.calender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.calender.dto.request.CalenderRequest;
import project.communityservice.domain.calender.dto.response.CalenderResponse;
import project.communityservice.domain.calender.entity.Calender;
import project.communityservice.domain.calender.service.CalenderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/calenders")
public class CalenderController {
    private final CalenderService calenderService;

    // 일정 전체 조회
    @GetMapping
    public ResponseEntity<List<CalenderResponse>> getAllCalenders() {
        return ResponseEntity.ok(calenderService.listCalender());
    }

    // 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<CalenderResponse> calenders(@PathVariable("id") Long id) {
        return ResponseEntity.ok(calenderService.getCalender(id));
    }

    // 일정 등록
    @PostMapping
    public ResponseEntity<CalenderResponse> createCalender(@RequestBody CalenderRequest calenderRequest) {
        return ResponseEntity.ok(calenderService.createCalender(calenderRequest.title(), calenderRequest.content()));
    }

    // 일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CalenderResponse> updateCalender(@PathVariable("id") Long id,
                                                           @RequestBody CalenderRequest calenderRequest) {
        return ResponseEntity.ok(calenderService.modifyCalender(id, calenderRequest.title(), calenderRequest.content()));
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void deleteCalender(@PathVariable("id") Long id) {
        calenderService.deleteCalender(id);
    }
}
