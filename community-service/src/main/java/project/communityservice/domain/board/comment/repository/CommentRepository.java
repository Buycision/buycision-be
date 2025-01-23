package project.communityservice.domain.board.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.board.comment.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment getByIdOrThrow(Long id){
        return findById(id).orElseThrow(null);
    }

    // 해당 게시물 모든 댓글
    @Query("SELECT c FROM Comment c WHERE c.board.id = :boardId")
    Page<Comment> findAllByBoardId(@Param("boardId") Long boardId);
}
