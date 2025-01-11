package project.communityservice.domain.board.service;

import project.communityservice.domain.board.dto.BoardRequest;
import project.communityservice.domain.board.dto.BoardResponse;

import java.util.List;

public interface BoardService {
    // 게시글 리스트
    List<BoardResponse> getBoards();

    // 게시글 단건 조회
    BoardResponse getBoard(Long boardId);

    // 게시글 등록
    BoardResponse createBoard(BoardRequest boardRequest);

    // 게시글 수정
    BoardResponse updateBoard(BoardRequest boardRequest);

    // 게시글 삭제
    void deleteBoard(Long boardId);
}
