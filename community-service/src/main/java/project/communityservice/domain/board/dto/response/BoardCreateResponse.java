package project.communityservice.domain.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.communityservice.domain.board.dto.BoardDTO;
import project.communityservice.domain.board.entity.Board;

@Getter
@AllArgsConstructor
public class BoardCreateResponse {
    private final BoardDTO board;
}
