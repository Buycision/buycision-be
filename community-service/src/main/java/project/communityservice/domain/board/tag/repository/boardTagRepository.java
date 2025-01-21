package project.communityservice.domain.board.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.board.tag.entity.boardTag;

@Repository
public interface boardTagRepository extends JpaRepository<boardTag, Long> {
}
