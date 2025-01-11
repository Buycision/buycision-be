package project.communityservice.domain.calender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.service.impl.CommunityServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityServiceImpl communityServiceImpl;

    // 게시물 목록
    @GetMapping
    public String boardList(){
        return "0";
    }

    // 게시물 등록
    @PostMapping
    public String

    // 게시물 단건 조회
    @GetMapping

    // 게시물 수정
    // Put 전체 , Patch 일정 부분
    @PatchMapping

    // 게시물 삭제
    @DeleteMapping

    // 회원목록
    @GetMapping

    // 회원 로그인
    @PostMapping

    // 로그인한 회원의 정보
    @GetMapping
}
