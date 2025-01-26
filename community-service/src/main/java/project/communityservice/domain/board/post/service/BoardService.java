package project.communityservice.domain.board.post.service;

import org.springframework.data.domain.Pageable;
import project.communityservice.domain.board.post.dto.response.BoardResponse;
import project.communityservice.domain.board.post.dto.response.CommentResponse;
import project.communityservice.domain.board.post.dto.response.TagResponse;

import java.util.List;

public interface BoardService {
    // 게시글 리스트
    List<BoardResponse> getBoards();

    // 게시글 단건 조회 (게시글, 태그, 댓글)
    BoardResponse getBoard(Long id);

    // 게시글 등록
    BoardResponse createBoardToArticle(String title, String content, Long tagId);

    // 게시글 수정
    BoardResponse updateBoard(Long id, String title, String content);

    // 게시글 삭제
    void deleteBoard(Long id);

    // 태그 검색
    List<BoardResponse> tagSearch(Long tagId, Pageable pageable);

    // 댓글 생성
    CommentResponse createComment(Long boardId, String body);

    // 댓글 삭제
    void deleteComment(Long boardId, Long CommentId);

    // 태그 생성
    TagResponse createTag(String tagName);

    // 게시글 생성
    BoardResponse createBoardToCommunity(String title, String content, Long tagId );
}
