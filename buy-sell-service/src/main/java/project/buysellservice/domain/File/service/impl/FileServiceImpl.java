package project.buysellservice.domain.File.service.impl;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.buysellservice.domain.File.service.FileService;
import project.buysellservice.domain.article.dto.response.FileResponse;
import project.buysellservice.domain.article.repository.ArticleRepository;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;
    private final ArticleRepository articleRepository;

    // 버킷 네임 생성
    @Override
    public String getBucketName() {
        TimeBasedGenerator generator = Generators.timeBasedGenerator();
        // UUID 버전 1 생성
        UUID uuid = generator.generate();

        return "post-" + uuid.toString().toLowerCase().replaceAll("-", "");
    }

    // 파일 업로드 과정 (생성, 수정 중복)
    @Override
    public FileResponse uploadFile(List<MultipartFile> files, String bucketName) throws Exception {
        // 이미지 url 저장
        List<String> urls = new ArrayList<>();

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

            urls.add(fileUrl);
        }

        return FileResponse.of(urls, bucketName);
    }

    // 파일 업로드
    @Override
    public FileResponse createFile(List<MultipartFile> files) throws Exception {
        // 버킷 이름 저장
        String bucketName = getBucketName();

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } // 버킷이 없으면 만들어주는 코드

        return uploadFile(files, bucketName);
    }

    @Override
    public FileResponse updateFile(List<MultipartFile> files, Long id) throws Exception {
        String bucketName = articleRepository.getByIdOrThrow(id).getBucketName();

        return uploadFile(files, bucketName);
    }


    // 이미지 파일 삭제하기
    @Override
    public void deleteFile(Long id) throws Exception {
        String bucketName = articleRepository.getByIdOrThrow(id).getBucketName();
        List<String> fileNames = getFileName(id);

        for (String file : fileNames) {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file)
                            .build()
            );

        }
    }

    // 버킷 전체 삭제하기
    @Override
    public void deleteBucket(Long id) throws Exception {
        String bucketName = articleRepository.getByIdOrThrow(id).getBucketName();

        deleteFile(id);

        minioClient.removeBucket(
                RemoveBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
    }

    // 이미지 url 에서 파일 이름 가져오기
    @Override
    public List<String> getFileName(Long id) {
        List<String> urls = articleRepository.getByIdOrThrow(id).getFiles();

        List<String> fileNames = new ArrayList<>();

        for (String url : urls) {
            String fileNameWithParams = url.substring(url.lastIndexOf("/") + 1);

            String fileName = fileNameWithParams.split("\\?")[0];

            fileNames.add(fileName);
        }

        return fileNames;
    }


}
