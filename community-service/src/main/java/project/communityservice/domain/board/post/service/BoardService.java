package project.communityservice.domain.board.post.service;

import project.communityservice.domain.board.post.dto.response.BoardResponse;

import java.util.List;

public interface BoardService {
    // 게시글 리스트
    List<BoardResponse> getBoards();

    // 게시글 단건 조회
    BoardResponse getBoard(Long id);

    // 게시글 등록
    BoardResponse createBoard(String title, String content);

    // 게시글 수정
    BoardResponse updateBoard(Long id, String title, String content);

    // 게시글 삭제
    void deleteBoard(Long id);

}
