package project.buysellservice.domain.article.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import project.buysellservice.domain.article.dto.response.ArticleResponse;

import java.util.List;

public interface ArticleService {
    // 게시글 생성
    ArticleResponse createArticle(String name, String content, List<MultipartFile> files, Long Price) throws Exception;

    // 게시글 읽기
    ArticleResponse readArticle(Long id);

    // 게시글 수정
    ArticleResponse updateArticle(Long id, String name, String content, MultipartFile file, Long Price) throws Exception;

    // 게시글 삭제
    void deleteArticle(Long id);

    // 전체 게시글 읽기
    List<ArticleResponse> readAllArticles(Pageable pageable);
}
