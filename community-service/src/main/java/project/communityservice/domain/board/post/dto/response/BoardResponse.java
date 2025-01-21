package project.communityservice.domain.board.post.dto.response;

import project.communityservice.domain.board.post.entity.Board;

import java.util.List;
import java.util.stream.Collectors;

public record BoardResponse (
        Long id,
        String title,
        String content
){
    public static BoardResponse of(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContent()
        );
    }

    public static List<BoardResponse> listOf(List<Board> boards) {
        return boards.stream()
                .map(BoardResponse::of)
                .collect(Collectors.toList());
    }
}
