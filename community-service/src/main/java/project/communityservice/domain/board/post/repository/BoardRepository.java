package project.communityservice.domain.board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.communityservice.domain.board.post.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    default Board getByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(null);
    }

    // 게시글 조회
    @Query("SELECT b FROM Board b join fetch b.comments c join fetch b.boardTag t WHERE b.id = :boardId")
    Board getById(@Param("boardId") Long boardId);

    @Query("SELECT b FROM Board b JOIN FETCH b.boardTag t WHERE t.id = :id")
    Page<Board> findAllByTag1(@Param("id") Long id, Pageable pageable);

    @Query("SELECT b FROM Board b JOIN b.boardTag t WHERE t.id = :id")
    Page<Board> findAllByTag(@Param("id") Long id, Pageable pageable);

}
