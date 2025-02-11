package project.buysellservice.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.buysellservice.domain.File.service.FileService;
import project.buysellservice.domain.article.dto.request.ArticleRequest;
import project.buysellservice.domain.article.dto.response.ArticleResponse;
import project.buysellservice.domain.article.service.ArticleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@Slf4j
public class ApiV1ArticleController {
    private final ArticleService articleService;
    private final FileService fileService;

    // 게시글 전체 페이징 처리
    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getArticle(@Valid @RequestParam("page") int page,
                                                            @Valid @RequestParam("size") int size) {
        return ResponseEntity.ok(articleService.readAllArticles(PageRequest.of(page, size)));
    }

    // 게시글 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(articleService.readArticle(id));
    }

    // 게시글 생성
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ArticleResponse> createArticle(@RequestPart("articleRequest") @Valid ArticleRequest articleRequest,
                                                         @RequestPart("file") List<MultipartFile> files) throws Exception {
        return ResponseEntity.ok(articleService.createArticle(
                articleRequest.title(), articleRequest.content(), files, articleRequest.price()
        ));
    }

    // 게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@Valid @PathVariable("id") Long id,
                                                         @RequestPart("articleRequest") ArticleRequest articleRequest,
                                                         @RequestPart MultipartFile file) throws Exception {
        return ResponseEntity.ok(articleService.updateArticle(
                id, articleRequest.title(), articleRequest.content(), file, articleRequest.price()
        ));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") Long id) throws Exception {
        fileService.deleteBucket(id);
        articleService.deleteArticle(id);
    }

}
