package project.buysellservice.domain.File.dto.response;

import project.buysellservice.domain.File.entity.File;

import java.util.List;

public record FileResponse(
        Long id,
        List<String> imageUrls,
        String bucketName

) {
    public static FileResponse of(File file) {
        return new FileResponse(
                file.getId(),
                file.getImageUrls(),
                file.getBucketName()
        );
    }
}
