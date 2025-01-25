package project.communityservice.domain.board.post.dto.response;

import project.communityservice.domain.board.comment.entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

public record CommentResponse(
        Long commentId,
        String body
) {

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getBody()
        );
    }

    public static List<CommentResponse> listOf(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }
        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }
}

