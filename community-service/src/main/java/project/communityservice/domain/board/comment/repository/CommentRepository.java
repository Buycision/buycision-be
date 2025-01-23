package project.communityservice.domain.board.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.board.comment.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment getByIdOrThrow(Long id){
        return findById(id).orElseThrow(null);
    }
}
