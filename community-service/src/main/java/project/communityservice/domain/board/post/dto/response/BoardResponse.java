package project.communityservice.domain.board.post.dto.response;

import project.communityservice.domain.board.post.entity.Board;

import java.util.List;
import java.util.stream.Collectors;

public record BoardResponse (
        Long boardId,
        String title,
        String content,
        TagResponse tagResponse,
        List<CommentResponse> commentResponse

){
    public static BoardResponse of(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                TagResponse.of(board.getBoardTag()),
                CommentResponse.listOf((board.getComments()))
        );
    }

    public static List<BoardResponse> listOf(List<Board> boards) {
        return boards.stream()
                .map(BoardResponse::of)
                .collect(Collectors.toList());
    }
}
