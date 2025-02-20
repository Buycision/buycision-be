package project.buysellservice.domain.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.domain.article.entity.Category;

public interface ArticleRepositoryCustom {
    // 전체 게시글 받아오기 (최신순)
    Page<Article> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 전체 게시글 받아오기 (오래된순)
    Page<Article> findAllByOrderByCreatedAtAsc(Pageable pageable);

    // 해당 카테고리 글 가져오기
    Page<Article> findAllByCategory(Category category, Pageable pageable);

    // 거래가능만 보기

    // 가격별로 보기 (나눔)

    // 가격별로 보기 (5000원 이하)

    // 가격별로 보기 (10000원 이하)

    // 가격별로 보기 (직접 설정)

}
