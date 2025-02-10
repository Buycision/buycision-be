package project.buysellservice.domain.File.service;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MinioClient minioClient;

    private String bucketName;

    // 파일 업로드
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename(); // 파일이름 가져오고
        InputStream fileStream = file.getInputStream(); // 파일 데이터를 읽고 가져온다.

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(fileName)
                .object(fileName)
                .stream(fileStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build(); // 파일 빌더

        minioClient.putObject(putObjectArgs);
        // 미니오 서버에 반환하는 응답 객체

        String imageUrl = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );

        return imageUrl;
    }

    // 이미지 다운로드
    @Override
    public InputStream downloadFile(String fileName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
    }

    // 이미지 파일 가져오기
    @Override
    public InputStream getFile(String fileName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
    }

    @Override
    public void deleteFile(String fileName) {

    }
}
