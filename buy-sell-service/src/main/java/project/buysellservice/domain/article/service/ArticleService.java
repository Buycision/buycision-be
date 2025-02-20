package project.buysellservice.domain.article.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import project.buysellservice.domain.article.dto.response.ArticleResponse;
import project.buysellservice.domain.article.entity.Category;

import java.util.List;

public interface ArticleService {
    // 게시글 생성
    ArticleResponse createArticle(String name, String content, List<MultipartFile> files, Long Price, Category category) throws Exception;

    // 게시글 읽기
    ArticleResponse readArticle(Long id);

    // 게시글 수정
    ArticleResponse updateArticle(Long id, String name, String content, List<MultipartFile> files, Long price, Category category) throws Exception;

    // 게시글 삭제
    void deleteArticle(Long id) throws Exception;

    // 전체 게시글 읽기
    List<ArticleResponse> readAllArticles(Pageable pageable);

    // 전체 게시글 읽기 (오래된순)
    List<ArticleResponse> readAllArticlesOrderByAsc(Pageable pageable);

    // 게시글 팔림 처리
    ArticleResponse soldArticle(Long id);

    // 카테고리 게시글 가져오기
    List<ArticleResponse> readCategoryArticle(Category category, Pageable pageable);

    // 구매가능 게시판만 보기
    List<ArticleResponse> readByStateSell(Pageable pageable);

    // 가격대 별로 게시판 보기
    List<ArticleResponse> readByPrice(Pageable pageable, int minPrice, int maxPrice);

}
