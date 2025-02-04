package project.communityservice.domain.board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import project.communityservice.domain.board.post.entity.Board;

public interface BoardRepositoryCustom {

    Page<Board> findAllByTag(@Param("id") Long id, Pageable pageable);
}
