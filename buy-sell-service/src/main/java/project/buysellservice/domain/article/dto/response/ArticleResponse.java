package project.buysellservice.domain.article.dto.response;

import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.domain.article.entity.State;

import java.util.List;
import java.util.stream.Collectors;

public record ArticleResponse(
        String title,
        String content,
        Long price,
        List<String> files,
        State state

) {
    public static ArticleResponse of(Article article) {
        return new ArticleResponse(
                article.getTitle(),
                article.getContent(),
                article.getPrice(),
                article.getFiles(),
                article.getState()

        );
    }

    public static List<ArticleResponse> listOf(List<Article> articles) {
        return articles.stream()
                .map(ArticleResponse::of)
                .collect(Collectors.toList());
    }
}
