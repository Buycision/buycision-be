package project.buysellservice.domain.article.dto.response;

import java.util.List;

public record FileResponse(
        String bucketName,
        List<String> files
) {
}
