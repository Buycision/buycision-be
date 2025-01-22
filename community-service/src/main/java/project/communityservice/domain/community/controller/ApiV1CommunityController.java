package project.communityservice.domain.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.community.dto.request.CommunityCreateRequest;
import project.communityservice.domain.community.dto.request.CommunityUpdateRequest;
import project.communityservice.domain.community.dto.response.CommunityResponse;
import project.communityservice.domain.community.service.CommunityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
public class ApiV1CommunityController {
    private final CommunityService communityService;

    // 커뮤니티 목록
    @GetMapping
    public ResponseEntity<List<CommunityResponse>> getCommunityList(
            @RequestParam int page,
            @RequestParam int size
    ){
        return ResponseEntity.ok(communityService.getCommunityList(PageRequest.of(page, size)));
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

    // 본인이 만든 커뮤니티 조회
    @GetMapping
    public ResponseEntity<List<CommunityResponse>> getCommunityByHost(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Long host
    ){
        return ResponseEntity.ok(communityService.getCommunityByHost(host, PageRequest.of(page,size)));
    }

    // 본인이 참여한 커뮤니티 조회
    @GetMapping
    public ResponseEntity<List<CommunityResponse>> getCommunityByParticipants(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Long participants
    ){
        return ResponseEntity.ok(communityService.getCommunityById(participants, PageRequest.of(page, size)));
    }
}
