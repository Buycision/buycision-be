package project.communityservice.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.communityservice.domain.board.entity.Board;

@Getter
@AllArgsConstructor
public class BoardResponse {
    private Board board;
}
