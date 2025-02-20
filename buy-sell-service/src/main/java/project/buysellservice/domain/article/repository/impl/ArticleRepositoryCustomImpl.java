package project.buysellservice.domain.article.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.domain.article.entity.Category;
import project.buysellservice.domain.article.entity.QArticle;
import project.buysellservice.domain.article.repository.ArticleRepositoryCustom;

import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Article> findAllByOrderByCreatedAt(Pageable pageable) {
        QArticle article = QArticle.article;

        List<Article> articles = jpaQueryFactory
                .selectFrom(article)
                .orderBy(article.createDate.desc())
                .offset(pageable.getOffset())  // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 한 페이지에 표시할 개수
                .fetch();

        return new PageImpl<>(articles);
    }

    @Override
    public Page<Article> findAllByCategory(Category category, Pageable pageable) {
        QArticle article = QArticle.article;

        List<Article> articles = jpaQueryFactory
                .selectFrom(article)
                .where(article.category.eq(category))
                .orderBy(article.createDate.desc())
                .offset(pageable.getOffset())  // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 한 페이지에 표시할 개수
                .fetch();

        return new PageImpl<>(articles);
    }
}
