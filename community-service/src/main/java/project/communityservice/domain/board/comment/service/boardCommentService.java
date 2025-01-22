package project.communityservice.domain.board.comment.service;

import project.communityservice.domain.board.comment.dto.request.CommentRequest;
import project.communityservice.domain.board.comment.dto.response.CommentResponse;

public interface boardCommentService {
    // 댓글 생성
    CommentResponse createComment(CommentRequest commentRequest);

    // 댓글 삭제
    void deleteComment(Long commentId);

}
