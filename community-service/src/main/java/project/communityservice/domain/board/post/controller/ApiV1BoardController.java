package project.communityservice.domain.board.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.board.post.dto.request.BoardCreateRequest;
import project.communityservice.domain.board.post.dto.request.BoardUpdateRequest;
import project.communityservice.domain.board.post.dto.request.CommentRequest;
import project.communityservice.domain.board.post.dto.request.TagCreateRequest;
import project.communityservice.domain.board.post.dto.response.BoardResponse;
import project.communityservice.domain.board.post.dto.response.CommentResponse;
import project.communityservice.domain.board.post.dto.response.TagResponse;
import project.communityservice.domain.board.post.service.BoardService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class ApiV1BoardController {
    private final BoardService boardService;

    // 게시글 목록
    @GetMapping
    public ResponseEntity<List<BoardResponse>> boardList() {
        return ResponseEntity.ok(boardService.getBoards());
    }

    // 게시물 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> board(@PathVariable("id") Long id) {
        return ResponseEntity.ok(boardService.getBoard(id));
    }

    // 게시물 등록 (게시판)
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardCreateRequest boardCreateRequest) {
        return ResponseEntity.ok(boardService.createBoardToArticle(boardCreateRequest.title(), boardCreateRequest.content(), boardCreateRequest.tagId()));
    }

    // 게시물 수정
    // Put 전체 , Patch 일정 부분
    @PatchMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@Valid @PathVariable("id") Long id,
                                                     @RequestBody BoardUpdateRequest boardUpdateRequest) {

        return ResponseEntity.ok(boardService.updateBoard(id, boardUpdateRequest.title(), boardUpdateRequest.content()));
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }

    // 태그를 만드는 그런 느낌
    @PostMapping("/tag")
    public ResponseEntity<TagResponse> createTag(@Valid @RequestBody TagCreateRequest tagCreateRequest) {
        return ResponseEntity.ok(boardService.createTag(tagCreateRequest.tagName()));
    }

    // 태그로 게시글 검색
    @GetMapping("/{tagId}/tag")
    public ResponseEntity<List<BoardResponse>> getTagToBoard(@Valid @PathVariable("tagId") Long tagId,
                                                             @RequestParam("page") int page,
                                                             @RequestParam("size") int size) {
        return ResponseEntity.ok(boardService.tagSearch(tagId, PageRequest.of(page, size)));
    }

    // 댓글 달기
    @PostMapping("/{id}")
    public ResponseEntity<CommentResponse> createComment(@Valid @PathVariable("id") Long id,
                                                         @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(boardService.createComment(id, commentRequest.body()));
    }

    // 댓글 삭제
    @DeleteMapping("/{id}/{commentId}")
    public void deleteComment(@Valid @PathVariable("id") Long id,
                              @PathVariable("commentId") Long commentId) {
        boardService.deleteComment(id, commentId);
    }
}
