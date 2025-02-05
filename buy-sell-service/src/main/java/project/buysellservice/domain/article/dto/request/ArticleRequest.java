package project.buysellservice.domain.article.dto.request;

public record ArticleRequest(
        String title,
        String content,
        String imageUrl,
        Long price
) {

}
