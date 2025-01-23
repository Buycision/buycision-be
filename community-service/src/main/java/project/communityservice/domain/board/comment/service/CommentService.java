package project.communityservice.domain.board.comment.service;

import org.springframework.data.domain.Pageable;
import project.communityservice.domain.board.comment.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    // 댓글 생성
    CommentResponse createComment(Long boardId, String body);

    // 댓글 삭제
    void deleteComment(Long boardId, Long CommentId);

    // 게시글 모든 댓글 조회
    List<CommentResponse> getComments(Long boardId, Pageable pageable);
}
