package project.communityservice.domain.board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.communityservice.domain.board.post.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    default Board getByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(null);
    }

    @Query("SELECT b FROM Board b JOIN b.boardTag t WHERE t.id = :id")
    Page<Board> findAllByTag(@Param("id") Long id, Pageable pageable);

}
