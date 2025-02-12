package project.buysellservice.domain.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.buysellservice.domain.article.entity.Article;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
    // 해당 게시글 가져오기
    default Article getByIdOrThrow(Long id) {
        return findById(id).orElseThrow(null);
        // 예외처리 만들기
    }

    // 버킷 네임 생성
    default String bucketName() {
        return "Post - " + UUID.randomUUID();
    }

}
