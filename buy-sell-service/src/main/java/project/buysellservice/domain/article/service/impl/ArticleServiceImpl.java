package project.buysellservice.domain.article.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.buysellservice.domain.File.service.FileService;
import project.buysellservice.domain.article.dto.response.ArticleResponse;
import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.domain.article.repository.ArticleRepository;
import project.buysellservice.domain.article.service.ArticleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final FileService fileService;

    // 생성
    @Override
    public ArticleResponse createArticle(String name, String content, MultipartFile file, Long price) throws Exception {
        String imageUrl = fileService.uploadFile(file, name);

        Article article = Article.createFrom(name, content, imageUrl, price);
        articleRepository.save(article);
        return ArticleResponse.of(article);
    }

    // 읽기
    @Override
    public ArticleResponse readArticle(Long id) {
        Article article = articleRepository.getByIdOrThrow(id);
        return ArticleResponse.of(article);
    }

    // 수정
    @Override
    public ArticleResponse updateArticle(Long id, String name, String content, MultipartFile file, Long price) throws Exception {
        fileService.deleteFile(id);

        String imageUrl = fileService.uploadFile(file, name);

        Article article = articleRepository.getByIdOrThrow(id);

        Article newArticle = Article.updateFrom(article.getId(), name, content, imageUrl, price);
        articleRepository.save(newArticle);
        return ArticleResponse.of(newArticle);
    }

    // 삭제
    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    // 모든 게시글 가져오기
    @Override
    public List<ArticleResponse> readAllArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAllByOrderByCreatedAt(pageable);
        List<Article> article = articles.getContent();
        return ArticleResponse.listOf(article);
    }

}
