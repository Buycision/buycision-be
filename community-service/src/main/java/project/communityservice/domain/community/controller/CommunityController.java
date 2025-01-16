package project.communityservice.domain.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.community.entity.Community;
import project.communityservice.domain.community.service.CommunityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    // 커뮤니티 목록
    @GetMapping
    public String communityList(){
        return "커뮤니티 목록입니다.";
    }

    // 커뮤니티 단건 조회
    @GetMapping("/{communityId}")
    public String community(
            @PathVariable Community communityId)
    {
        return "커뮤니티 단건 조회입니다.";
    }

    // 커뮤니티 생성
    @PostMapping
    public String addCommunity(
            @RequestBody Community community)
    {
        return "커뮤니티 생성입니다.";
    }

    // 커뮤니티 수정
    @PatchMapping("/{communityId}")
    public String updateCommunity(
            @PathVariable Community communityId,
            @RequestBody Community community)
    {
        return "커뮤니티 수정입니다.";
    }

    // 커뮤니티 삭제
    @DeleteMapping
    public String deleteCommunity(){
        return "커뮤니티 삭제입니다.";
    }
}
