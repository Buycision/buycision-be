package project.communityservice.domain.board.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.board.comment.entity.boardComment;

@Repository
public interface boardCommentRepository extends JpaRepository<boardComment, Long> {
}
