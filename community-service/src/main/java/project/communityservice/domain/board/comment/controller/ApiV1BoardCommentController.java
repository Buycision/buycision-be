package project.communityservice.domain.board.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.board.comment.dto.request.CommentRequest;
import project.communityservice.domain.board.comment.dto.response.CommentResponse;
import project.communityservice.domain.board.comment.service.impl.CommentServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class ApiV1BoardCommentController {
    private final CommentServiceImpl commentServiceImpl;

    /**
     * 댓글 생성
     */
    @PostMapping("/{boardId}")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable("boardId") Long boardId,
            @Valid @RequestBody CommentRequest commentRequest
    ){
        return ResponseEntity.ok(commentServiceImpl.createComment(boardId, commentRequest.body()));
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{boardId}/{commentId}")
    public void deleteComment(
            @PathVariable("boardId") Long boardId,
            @PathVariable("commentId") Long commentId
    ){
      commentServiceImpl.deleteComment(boardId, commentId);
    }

    // 댓글 가져오는 컨트롤러는 게시글에 만드는게 더 적합하다고 생각중
}
