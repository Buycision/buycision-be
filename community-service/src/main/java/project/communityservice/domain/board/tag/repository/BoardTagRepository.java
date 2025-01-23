package project.communityservice.domain.board.tag.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.board.tag.entity.BoardTag;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
    // 태그 찾기
    @Query("SELECT t FROM BoardTag t JOIN t.board b WHERE t.tag = :tagName")
    Page<BoardTag> findAllByTag(@Param("tagName") String tagName, Pageable pageable);
}
