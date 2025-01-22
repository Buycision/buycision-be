package project.communityservice.domain.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.board.post.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 단건 조회
    default Board getByIdOrThrow(Long id){
        return findById(id).orElseThrow();
    }
}
