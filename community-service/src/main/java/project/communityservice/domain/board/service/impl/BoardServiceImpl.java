package project.communityservice.domain.board.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.board.dto.response.BoardResponse;
import project.communityservice.domain.board.entity.Board;
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
        List<Board> boards = boardRepository.findAll();

        return BoardResponse.listOf(boards);
    }

    @Override
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.getByIdOrThrow(id);

        return BoardResponse.of(board);
    }

    @Override
    public BoardResponse createBoard(String title, String content) {
        Board board = Board.createFrom(title, content);
        boardRepository.save(board);
        return BoardResponse.of(board);
    }

    @Override
    public BoardResponse updateBoard(Long id, String title, String content) {
        Board board = boardRepository.getByIdOrThrow(id);
        Board newBoard = Board.updateFrom(board.getId(), title, content);
        boardRepository.save(newBoard);
        return BoardResponse.of(newBoard);
    }

    @Override
    public void deleteBoard(Long id) {
        Board board = boardRepository.getByIdOrThrow(id);
        boardRepository.delete(board);
    }
}
