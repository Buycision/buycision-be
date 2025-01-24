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

import javax.swing.text.html.HTML;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardTagRepository boardTagRepository;
    private final CommentRepository commentRepository;

    // 모든 게시글 가져오기
    @Override
    public List<BoardResponse> getBoards() {
        List<Board> boards = boardRepository.findAll();

        return BoardResponse.listOf(boards);
    }

    // 특정 게시글 가져오기
    @Override
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.getByIdOrThrow(id);

        return BoardResponse.of(board);
    }

    // 게시글 생성
    @Override
    public BoardResponse createBoard(String title, String content, String tagName) {
        Board board = Board.createFrom(title, content, tagName);
        boardRepository.save(board);
        return BoardResponse.of(board);
    }

    // 게시글 업데이트
    @Override
    public BoardResponse updateBoard(Long id, String title, String content) {
        Board board = boardRepository.getByIdOrThrow(id);
        Board newBoard = Board.updateFrom(board.getId(), title, content);
        boardRepository.save(newBoard);
        return BoardResponse.of(newBoard);
    }

    // 게시글 삭제
    @Override
    public void deleteBoard(Long id) {
        Board board = boardRepository.getByIdOrThrow(id);
        boardRepository.delete(board);
    }

    @Override
    public List<BoardResponse> tagSerach(String tagName, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByTag(tagName, pageable);
        List<Board> boardTags = boards.getContent();

        return BoardResponse.listOf(boardTags);
    }

    @Override
    public CommentResponse createComment(Long boardId, String body) {
        Board board = boardRepository.getByIdOrThrow(boardId);

        Comment comment = Comment.from(board, body);

        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }

    @Override
    public void deleteComment(Long boardId, Long commentId) {
        // 해당 게시글에 해당하는 댓글 지우기위해 boardId 생성
//        Board board = boardRepository.getByIdOrThrow(boardId);

        Comment comment = commentRepository.getByIdOrThrow(commentId);

        commentRepository.delete(comment);
    }

    @Override
    public TagResponse createTag(String tagName){
        return null;
    }
}
