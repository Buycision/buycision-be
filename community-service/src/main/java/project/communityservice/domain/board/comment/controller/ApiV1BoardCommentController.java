package project.communityservice.domain.board.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.communityservice.domain.board.comment.dto.request.CommentRequest;
import project.communityservice.domain.board.comment.dto.response.CommentResponse;
import project.communityservice.domain.board.comment.service.impl.CommentServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board/comment")
public class ApiV1BoardCommentController {
    private final CommentServiceImpl commentServiceImpl;

//    /**
//     * 게시물 댓글 조회
//     */
//    @GetMapping
//    public ResponseEntity<CommentResponse> allComment(){
//        return ResponseEntity.ok(null);
//    }


    /**
     * 댓글 생성
     */
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody CommentRequest commentRequest
    ){
        return ResponseEntity.ok(commentServiceImpl.createComment(commentRequest.body()));
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{id}")
    public void deleteComment(
            @PathVariable("id") Long id
    ){
      commentServiceImpl.deleteComment(id);
    }


}
