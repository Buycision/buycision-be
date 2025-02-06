package project.communityservice.domain.board.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.board.tag.entity.BoardTag;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
    default BoardTag getByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(null);
    }
}
