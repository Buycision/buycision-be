package project.communityservice.domain.board.tag.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.board.tag.dto.request.TagRequest;
import project.communityservice.domain.board.tag.dto.response.TagResponse;
import project.communityservice.domain.board.tag.service.BoardTagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class ApiV1TagController {
    private final BoardTagService boardTagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getTagBoards(
            @RequestParam int size,
            @RequestParam int page,
            @Valid @RequestBody TagRequest tagRequest
    ){
        return ResponseEntity.ok(boardTagService.tagSerach(tagRequest.tagName(), PageRequest.of(size, page)));
    }
}
