package project.buysellservice.domain.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.domain.article.entity.Category;

public interface ArticleRepositoryCustom {
    // 전체 게시글 받아오기
    Page<Article> findAllByOrderByCreatedAt(Pageable pageable);

    // 해당 카테고리 글 가져오기
    Page<Article> findAllByCategory(Category category, Pageable pageable);
}
