package project.communityservice.domain.board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.board.post.exception.BoardException;
import project.communityservice.global.exception.ErrorCode;

public interface BoardRepository extends JpaRepository<Board, Long> {
    default Board getByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new BoardException(ErrorCode.NOT_FOUND_BOARD));
    }

    @Query("SELECT b FROM Board b JOIN b.boardTag t WHERE t.id = :id")
    Page<Board> findAllByTag(@Param("id") Long id, Pageable pageable);

}
