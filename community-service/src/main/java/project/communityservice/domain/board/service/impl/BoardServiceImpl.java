package project.communityservice.domain.board.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.board.dto.BoardRequest;
import project.communityservice.domain.board.dto.BoardResponse;
import project.communityservice.domain.board.repository.BoardRepository;
import project.communityservice.domain.board.service.BoardService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public List<BoardResponse> getBoards() {
        return List.of();
    }

    @Override
    public BoardResponse getBoard(Long boardId) {
        return null;
    }

    @Override
    public BoardResponse createBoard(BoardRequest boardRequest) {
        return null;
    }

    @Override
    public BoardResponse updateBoard(BoardRequest boardRequest) {
        return null;
    }

    @Override
    public void deleteBoard(Long boardId) {

    }
}
