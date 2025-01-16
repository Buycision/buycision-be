package project.communityservice.domain.board.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.board.dto.BoardDTO;
import project.communityservice.domain.board.entity.Board;
import project.communityservice.domain.board.repository.BoardRepository;
import project.communityservice.domain.board.service.BoardService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public List<BoardDTO> getBoards() {
        List<Board> boards = boardRepository.findAll();

        List<BoardDTO> boardDTOs = boards.stream()
                .map(BoardDTO::new)
                .collect(Collectors.toList());

        return boardDTOs;
    }

    @Override
    public BoardDTO getBoard(Long id) {
        Board board = boardRepository.getByIdOrThrow(id);

        BoardDTO boardDTO = new BoardDTO(board);

        return boardDTO;
    }

    @Override
    public BoardDTO createBoard(String title, String content) {
        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();

        Board savedBoard = boardRepository.save(board);

        BoardDTO boardDTO = new BoardDTO(savedBoard);

        return boardDTO;
    }

    @Override
    public BoardDTO updateBoard(Board board, String title, String content) {
        Board newBoard = Board.builder()
                .id(board.getId())
                .title(title)
                .content(content)
                .build();

        Board savedBoard = boardRepository.save(newBoard);

        BoardDTO boardDTO = new BoardDTO(savedBoard);

        return boardDTO;
    }

    @Override
    public void deleteBoard(Long id) {
        Board board = boardRepository.getByIdOrThrow(id);
        boardRepository.delete(board);
    }
}
