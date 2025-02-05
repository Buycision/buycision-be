package project.buysellservice.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.buysellservice.domain.article.dto.request.ArticleRequest;
import project.buysellservice.domain.article.dto.response.ArticleResponse;
import project.buysellservice.domain.article.service.ArticleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ApiV1ArticleController {
    private final ArticleService articleService;

    // 게시글 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(articleService.readArticle(id));
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(articleService.createArticle(
                articleRequest.title(), articleRequest.content(), articleRequest.imageUrl(), articleRequest.price()
        ));
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@Valid @PathVariable("id") Long id,
                                                         @RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(articleService.updateArticle(
                id, articleRequest.title(), articleRequest.content(), articleRequest.imageUrl(), articleRequest.price()
        ));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);
    }

}
