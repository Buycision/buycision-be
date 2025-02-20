package project.buysellservice.domain.article.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ArticleRequest(
        String title,
        String content,
        Long price
) {

}
