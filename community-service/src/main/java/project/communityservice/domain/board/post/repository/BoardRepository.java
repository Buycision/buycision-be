package project.communityservice.domain.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.board.post.exception.BoardException;
import project.communityservice.global.exception.ErrorCode;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    default Board getByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new BoardException(ErrorCode.NOT_FOUND_BOARD));
    }
}
