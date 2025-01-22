package project.communityservice.domain.board.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.board.comment.dto.response.CommentResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/")
public class ApiV1BoardCommentController {

    /**
     * 게시물 댓글 조회
     */
    @GetMapping
    public ResponseEntity<CommentResponse> allComment(){
        return ResponseEntity.ok(null);
    }


    /**
     * 댓글 생성
     */
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(){
        return ResponseEntity.ok(null);
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping
    public ResponseEntity<CommentResponse> deleteComment(){
        return ResponseEntity.ok(null);
    }


}
