package project.communityservice.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.board.entity.Board;

@Repository
public interface CommunityRepository extends JpaRepository<Board, Long> {
}
