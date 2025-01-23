package project.communityservice.domain.board.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.communityservice.domain.board.comment.dto.response.CommentResponse;
import project.communityservice.domain.board.comment.entity.Comment;
import project.communityservice.domain.board.comment.repository.CommentRepository;
import project.communityservice.domain.board.comment.service.CommentService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public CommentResponse createComment(String body) {
        Comment comment = Comment.from(body);

        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.getByIdOrThrow(id);

        commentRepository.delete(comment);
    }
}
