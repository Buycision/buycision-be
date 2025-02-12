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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final FileService fileService;

    // 모든 게시글 가져오기
    @Override
    public List<ArticleResponse> readAllArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAllByOrderByCreatedAt(pageable);

        List<Article> article = articles.getContent();

        return ArticleResponse.listOf(article);
    }

    // 단일 게시글 가져오기
    @Override
    public ArticleResponse readArticle(Long id) {
        Article article = articleRepository.getByIdOrThrow(id);

        return ArticleResponse.of(article);
    }


    // 생성
    @Override
    public ArticleResponse createArticle(String name, String content, List<MultipartFile> files, Long price) throws Exception {
        Map<String, Object> fileData = fileService.createFile(files);

        String bucketName = (String) fileData.get("bucket");

        List<String> images = (List<String>) fileData.get("urls");

        Article article = Article.createFrom(name, content, images, price, bucketName);
        articleRepository.save(article);
        return ArticleResponse.of(article);
    }

    // 수정
    @Override
    public ArticleResponse updateArticle(Long id, String name, String content, List<MultipartFile> files, Long price) throws Exception {
        fileService.deleteFile(id);

        Map<String, Object> fileData = fileService.updateFile(files, id);

        String bucketName = (String) fileData.get("bucket");

        List<String> images = (List<String>) fileData.get("urls");

        Article article = articleRepository.getByIdOrThrow(id);

        Article newArticle = Article.updateFrom(article.getId(), name, content, images, price, bucketName);

        articleRepository.save(newArticle);

        return ArticleResponse.of(newArticle);
    }

    // 삭제
    @Override
    public void deleteArticle(Long id) throws Exception {
        fileService.deleteBucket(id);

        articleRepository.deleteById(id);
    }

}
