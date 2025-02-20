package project.buysellservice.domain.article.dto.request;

import org.springframework.web.multipart.MultipartFile;
import project.buysellservice.domain.article.entity.Category;

public record ArticleRequest(
        String title,
        String content,
        Long price,
        Category category
) {

}
