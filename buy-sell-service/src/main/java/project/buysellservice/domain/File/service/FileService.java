package project.buysellservice.domain.File.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {
    // 파일 업로드
    Map<String, Object> uploadFile(List<MultipartFile> files, String bucketName) throws Exception;

    // 파일 생성하기
    Map<String, Object> createFile(List<MultipartFile> files) throws Exception;

    // 파일 수정하기
    Map<String, Object> updateFile(List<MultipartFile> files, Long id) throws Exception;

    // 파일 삭제하기
    void deleteFile(Long id) throws Exception;

    // 버킷 삭제하기
    void deleteBucket(Long id) throws Exception;

    // 파일 이름 가져오기
    List<String> getFileName(Long id) throws Exception;

    // 버킷 이름 생성
    String getBucketName() throws Exception;
}
