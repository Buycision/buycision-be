package project.communityservice.domain.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.communityservice.domain.board.dto.BoardDTO;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardsResponse {
    private final List<BoardDTO> boards;
}
