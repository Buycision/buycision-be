package project.buysellservice.domain.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.buysellservice.domain.article.entity.Article;

public interface ArticleRepositoryCustom {
    // 전체 게시글 받아오기
    Page<Article> findAll(Pageable pageable);
}
