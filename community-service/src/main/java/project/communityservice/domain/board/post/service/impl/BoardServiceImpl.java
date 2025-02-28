package project.communityservice.domain.board.post.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.communityservice.domain.board.comment.entity.Comment;
import project.communityservice.domain.board.comment.repository.CommentRepository;
import project.communityservice.domain.board.post.dto.response.BoardResponse;
import project.communityservice.domain.board.post.dto.response.CommentResponse;
import project.communityservice.domain.board.post.dto.response.TagResponse;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.board.post.repository.BoardRepository;
import project.communityservice.domain.board.post.service.BoardService;
import project.communityservice.domain.board.tag.entity.BoardTag;
import project.communityservice.domain.board.tag.repository.BoardTagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardTagRepository boardTagRepository;
    private final CommentRepository commentRepository;

    // 모든 게시글 가져오기
    @Override
    public List<BoardResponse> getBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        List<Board> board = boards.getContent();

        return BoardResponse.listOf(board);
    }

    // 특정 게시글 가져오기
    @Override
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.getByIdOrElseThrow(id);

        return BoardResponse.of(board);
    }

    // 게시글 생성
    @Override
    public BoardResponse createBoard(String title, String content, Long tagId) {
        Board board = Board.createFrom(title, content, tagId);
        boardRepository.save(board);
        return BoardResponse.of(board);
    }

    // 게시글 업데이트
    @Override
    public BoardResponse updateBoard(Long id, String title, String content) {
        Board board = boardRepository.getByIdOrElseThrow(id);
        Board newBoard = Board.updateFrom(board.getId(), title, content, board.getBoardTag().getId());
        boardRepository.save(newBoard);
        return BoardResponse.of(newBoard);
    }

    // 게시글 삭제
    @Override
    public void deleteBoard(Long id) {
        Board board = boardRepository.getByIdOrElseThrow(id);
        boardRepository.delete(board);
    }

    // 태그로 게시글 검색
    @Override
    public List<BoardResponse> tagSearch(Long tagId, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByTag(tagId, pageable);
        List<Board> boardTags = boards.getContent();

        return BoardResponse.listOf(boardTags);
    }

    @Override
    public CommentResponse createComment(Long boardId, String body) {
        Board board = boardRepository.getByIdOrElseThrow(boardId);

        Comment comment = Comment.from(board, body);

        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }

    @Override
    public void deleteComment(Long boardId, Long commentId) {
        Comment comment = commentRepository.getByIdOrThrow(commentId);

        commentRepository.delete(comment);
    }

    @Override
    public TagResponse createTag(String tagName) {
        BoardTag boardTag = BoardTag.createFrom(tagName);

        boardTagRepository.save(boardTag);

        return TagResponse.of(boardTag);
    }
}
