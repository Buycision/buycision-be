package project.buysellservice.domain.File.service.impl;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.buysellservice.domain.File.dto.response.FileResponse;
import project.buysellservice.domain.File.entity.File;
import project.buysellservice.domain.File.repository.FileRepository;
import project.buysellservice.domain.File.service.FileService;
import project.buysellservice.domain.article.repository.ArticleRepository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;
    private final ArticleRepository articleRepository;
    private final FileRepository fileRepository;

    // 파일 업로드
    @Override
    public FileResponse uploadFile(List<MultipartFile> files) throws Exception {
        List<String> uploadedUrls = new ArrayList<>();
        String bucketName = "Post - " + UUID.randomUUID(); // 해당 고유값을 사용하여 게시글과 연관되게 만들고 싶음

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } // 버킷이 없으면 만들어주는 코드

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename(); // 파일이름 가져오고
            InputStream fileStream = file.getInputStream(); // 파일 데이터를 읽고 가져온다.

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(fileStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build(); // 파일 빌더

            minioClient.putObject(putObjectArgs);
            // 미니오 서버에 반환하는 응답 객체

            String fileUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );

            uploadedUrls.add(fileUrl);
        }

        File file = File.createFrom(uploadedUrls, bucketName);

        fileRepository.save(file);

        return FileResponse.of(file);
    }

    // 이미지 파일 삭제하기
    @Override
    public void deleteFile(Long id) throws Exception {
        String bucketName = articleRepository.getByIdOrThrow(id).getTitle();
        String fileName = getFileName(id);

        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
    }

    // 버킷 전체 삭제하기
    @Override
    public void deleteBucket(Long id) throws Exception {
        String bucketName = articleRepository.getByIdOrThrow(id).getTitle();
        String fileName = getFileName(id);

        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );

        minioClient.removeBucket(
                RemoveBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
    }

    // 이미지 url 에서 파일 이름 가져오기
    @Override
    public String getFileName(Long id) {
        String url = articleRepository.getByIdOrThrow(id).getImageUrl();

        String fileNameWithParams = url.substring(url.lastIndexOf("/") + 1);

        return fileNameWithParams.split("\\?")[0];
    }


}
