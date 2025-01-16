package project.communityservice.domain.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.community.dto.request.CommunityCreateRequest;
import project.communityservice.domain.community.dto.request.CommunityUpdateRequest;
import project.communityservice.domain.community.dto.response.CommunityResponse;
import project.communityservice.domain.community.dto.response.CommunityResponses;
import project.communityservice.domain.community.service.CommunityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
public class ApiV1CommunityController {
    private final CommunityService communityService;

    // 커뮤니티 목록
    @GetMapping
    public ResponseEntity<List<CommunityResponses>> getCommunityList(){
        return ResponseEntity.ok(communityService.getCommunityList());
    }

    // 커뮤니티 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommunityResponse> getCommunity(@PathVariable("id") Long id){
        return ResponseEntity.ok(communityService.getCommunity(id));
    }

    // 커뮤니티 생성
    @PostMapping
    public ResponseEntity<CommunityResponse> createCommunity(
            @Valid @RequestBody CommunityCreateRequest communityCreateRequest)
    {
        return ResponseEntity.ok(communityService.createCommunity(communityCreateRequest.name(), communityCreateRequest.description()));
    }

    // 커뮤니티 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommunityResponse> updateCommunity(
            @PathVariable("id") Long id,
            @Valid @RequestBody CommunityUpdateRequest communityUpdateRequest)
    {
        return ResponseEntity.ok(communityService.updateCommunity(id, communityUpdateRequest.name(), communityUpdateRequest.description()));
    }

    // 커뮤니티 삭제
    @DeleteMapping("/{id}")
    public void deleteCommunity(@PathVariable("id") Long id){
        communityService.deleteCommunityById(id);
    }
}
