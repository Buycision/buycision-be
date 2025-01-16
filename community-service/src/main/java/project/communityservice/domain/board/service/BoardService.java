package project.communityservice.domain.board.service;

import project.communityservice.domain.board.dto.BoardDTO;
import project.communityservice.domain.board.entity.Board;

import java.util.List;

public interface BoardService {
    // 게시글 리스트
    List<BoardDTO> getBoards();

    // 게시글 단건 조회
    BoardDTO getBoard(Long id);

    // 게시글 등록
    BoardDTO createBoard(String title, String content);

    // 게시글 수정
    BoardDTO updateBoard(Board board, String title, String content);

    // 게시글 삭제
    void deleteBoard(Long id);

}
