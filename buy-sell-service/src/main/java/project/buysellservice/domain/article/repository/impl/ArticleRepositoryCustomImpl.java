package project.buysellservice.domain.article.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.domain.article.entity.Category;
import project.buysellservice.domain.article.entity.QArticle;
import project.buysellservice.domain.article.entity.State;
import project.buysellservice.domain.article.repository.ArticleRepositoryCustom;

import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    // 다음 페이지가 있는지 여부 확인
    public static <T> boolean checkHasNext(List<T> items, Pageable pageable) {
        boolean hasNext = false;

        if (items.size() > pageable.getPageSize()) {
            items.remove(items.size() - 1); // 마지막 요소 제거
            hasNext = true;
        }

        return hasNext;
    }

    @Override
    public Slice<Article> findAllByOrderByCreatedAtDesc(Pageable pageable) {
        QArticle article = QArticle.article;

        List<Article> articles = jpaQueryFactory
                .selectFrom(article)
                .orderBy(article.createDate.desc())
                .offset(pageable.getOffset())  // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 한 페이지에 표시할 개수
                .fetch();


        return new SliceImpl<>(articles, pageable, checkHasNext(articles, pageable));
    }

    @Override
    public Slice<Article> findAllByOrderByCreatedAtAsc(Pageable pageable) {
        QArticle article = QArticle.article;

        List<Article> articles = jpaQueryFactory
                .selectFrom(article)
                .orderBy(article.createDate.asc())
                .offset(pageable.getOffset())  // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 한 페이지에 표시할 개수
                .fetch();

        return new SliceImpl<>(articles, pageable, checkHasNext(articles, pageable));
    }

    @Override
    public Slice<Article> findAllByCategory(Category category, Pageable pageable) {
        QArticle article = QArticle.article;

        List<Article> articles = jpaQueryFactory
                .selectFrom(article)
                .where(article.category.eq(category))
                .orderBy(article.createDate.desc())
                .offset(pageable.getOffset())  // 페이지 시작 위치
                .limit(pageable.getPageSize()) // 한 페이지에 표시할 개수
                .fetch();

        return new SliceImpl<>(articles, pageable, checkHasNext(articles, pageable));
    }

    @Override
    public Slice<Article> findBySellStatus(Pageable pageable) {
        QArticle article = QArticle.article;

        List<Article> articles = jpaQueryFactory
                .selectFrom(article)
                .where(article.state.eq(State.SELL))
                .orderBy(article.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(articles, pageable, checkHasNext(articles, pageable));

    }

    @Override
    public Slice<Article> findByPrice(Pageable pageable, int minPrice, int maxPrice) {
        QArticle article = QArticle.article;

        List<Article> articles = jpaQueryFactory
                .selectFrom(article)
                .where(article.price.goe(minPrice), article.price.loe(maxPrice))
                .orderBy(article.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(articles, pageable, checkHasNext(articles, pageable));
    }
}
