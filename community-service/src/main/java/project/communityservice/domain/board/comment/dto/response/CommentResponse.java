package project.communityservice.domain.board.comment.dto.response;

import project.communityservice.domain.board.comment.entity.Comment;

public record CommentResponse(
        Long id,
        String body
) {

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getBody()
        );
    }
}
