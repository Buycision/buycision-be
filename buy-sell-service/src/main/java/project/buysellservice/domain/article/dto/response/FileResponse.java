package project.buysellservice.domain.article.dto.response;

import project.buysellservice.domain.article.entity.Article;

import java.util.List;

public record FileResponse(
        List<String> files,
        String bucketName
) {
    public static FileResponse of(List<String> urls, String bucketName) {
        return new FileResponse(
                urls,
                bucketName
        );
    }

}
