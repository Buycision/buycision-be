package project.buysellservice.domain.article.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.domain.article.repository.ArticleRepositoryCustom;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return null;
    }
}
