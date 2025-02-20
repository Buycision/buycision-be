package project.buysellservice.domain.File.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    // 파일 업로드
    String uploadFile(MultipartFile file, String bucketName) throws Exception;

    // 파일 삭제하기
    void deleteFile(Long id) throws Exception;

    // 버킷 삭제하기
    void deleteBucket(Long id) throws Exception;

    // 파일 이름 가져오기
    String getFileName(Long id) throws Exception;
}
