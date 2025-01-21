package project.communityservice.domain.board.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.communityservice.domain.board.comment.dto.request.CommentRequest;
import project.communityservice.domain.board.comment.dto.response.CommentResponse;
import project.communityservice.domain.board.comment.repository.boardCommentRepository;
import project.communityservice.domain.board.comment.service.boardCommentService;

import javax.xml.stream.events.Comment;

@Service
@RequiredArgsConstructor
public class boardCommentServiceImpl implements boardCommentService {
    private final boardCommentRepository commentRepository;

    @Override
    public CommentResponse createComment(CommentRequest commentRequest) {
        return "";
    }

    @Override
    public void deleteComment(Long id) {

    }
}
