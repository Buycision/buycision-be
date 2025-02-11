package project.buysellservice.domain.article.dto.response;

import project.buysellservice.domain.File.dto.response.FileResponse;
import project.buysellservice.domain.File.entity.File;
import project.buysellservice.domain.article.entity.Article;

import java.util.List;
import java.util.stream.Collectors;

public record ArticleResponse(
        String title,
        String content,
        Long price,
        FileResponse fileResponse

) {
    public static ArticleResponse of(Article article) {
        return new ArticleResponse(
                article.getTitle(),
                article.getContent(),
                article.getPrice(),
                FileResponse.of(article.getFile())

        );
    }

    public static List<ArticleResponse> listOf(List<Article> articles) {
        return articles.stream()
                .map(ArticleResponse::of)
                .collect(Collectors.toList());
    }
}
