package project.communityservice.domain.board.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.communityservice.domain.board.comment.dto.response.CommentResponse;
import project.communityservice.domain.board.comment.entity.Comment;
import project.communityservice.domain.board.comment.repository.CommentRepository;
import project.communityservice.domain.board.comment.service.CommentService;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.board.post.repository.BoardRepository;
import project.communityservice.domain.community.dto.response.CommunityResponse;
import project.communityservice.domain.community.entity.Community;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public CommentResponse createComment(Long boardId, String body) {
        Board board = boardRepository.getByIdOrThrow(boardId);

        Comment comment = Comment.from(board, body);

        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }

    @Override
    public void deleteComment(Long boardId, Long commentId) {
        // 해당 게시글에 해당하는 댓글 지우기위해 boardId 생성
//        Board board = boardRepository.getByIdOrThrow(boardId);

        Comment comment = commentRepository.getByIdOrThrow(commentId);

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentResponse> getComments(Long boardId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findAllByBoardId(boardId);
        List<Comment> commentList = comments.getContent();

        return CommentResponse.listOf(commentList);
    }
}
