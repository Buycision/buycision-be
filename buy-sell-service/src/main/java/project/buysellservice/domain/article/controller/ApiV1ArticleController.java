package project.buysellservice.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.buysellservice.domain.article.dto.request.ArticleRequest;
import project.buysellservice.domain.article.dto.response.ArticleResponse;
import project.buysellservice.domain.article.entity.Category;
import project.buysellservice.domain.article.service.ArticleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@Slf4j
public class ApiV1ArticleController {
    private final ArticleService articleService;

    // 게시글 전체 페이징 처리
    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getArticle(Pageable pageable) {
        return ResponseEntity.ok(articleService.readAllArticles(pageable));
    }

    // 게시글 전체 (오래된순)
    @GetMapping("/asc")
    public ResponseEntity<List<ArticleResponse>> getArticleAsc(Pageable pageable) {
        return ResponseEntity.ok(articleService.readAllArticlesOrderByAsc(pageable));
    }

    // 게시글 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(articleService.readArticle(id));
    }

    // 구매가능 게시글만 보기
    @GetMapping("/stateSell")
    public ResponseEntity<List<ArticleResponse>> getArticleStateSell(Pageable pageable) {
        return ResponseEntity.ok(articleService.readByStateSell(pageable));
    }

    // 가격대 별로 게시판 보기
    @GetMapping("/price")
    public ResponseEntity<List<ArticleResponse>> getArticlePrice(Pageable pageable,
                                                                 @Valid @RequestParam("minPrice") int minPrice,
                                                                 @Valid @RequestParam("maxPrice") int maxPrice) {
        return ResponseEntity.ok(articleService.readByPrice(pageable, minPrice, maxPrice));
    }

    // 게시글 생성
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ArticleResponse> createArticle(@RequestPart("articleRequest") @Valid ArticleRequest articleRequest,
                                                         @RequestPart("file") List<MultipartFile> files) throws Exception {
        return ResponseEntity.ok(articleService.createArticle(
                articleRequest.title(), articleRequest.content(), files, articleRequest.price(), articleRequest.category()
        ));
    }

    // 게시글 수정
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ArticleResponse> updateArticle(@Valid @PathVariable("id") Long id,
                                                         @RequestPart("articleRequest") ArticleRequest articleRequest,
                                                         @RequestPart("file") List<MultipartFile> files) throws Exception {
        return ResponseEntity.ok(articleService.updateArticle(
                id, articleRequest.title(), articleRequest.content(), files, articleRequest.price(), articleRequest.category()
        ));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") Long id) throws Exception {
        articleService.deleteArticle(id);
    }

    // 해당 물품 판매 처리 -> 게시글 솔드 상태
    @PatchMapping("/{id}")
    public ResponseEntity<ArticleResponse> soldArticle(@Valid @PathVariable("id") Long id){
        return ResponseEntity.ok(articleService.soldArticle(id));
    }

    // 해당 카테고리 글만 가져오기
    @GetMapping("/category")
    public ResponseEntity<List<ArticleResponse>> getArticleByCategory(@Valid @RequestParam("category") Category category,
                                                                      Pageable pageable) {
        return ResponseEntity.ok(articleService.readCategoryArticle(category, pageable));
    }

}
