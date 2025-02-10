package project.buysellservice.domain.File.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
    // 파일 업로드
    String uploadFile(MultipartFile file) throws Exception;

    // 파일 다운로드
    InputStream downloadFile(String fileName) throws Exception;

    // 파일 가져오기
    InputStream getFile(String fileName) throws Exception;

    // 파일 삭제하기


}
