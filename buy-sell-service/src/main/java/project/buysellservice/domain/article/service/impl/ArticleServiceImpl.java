package project.buysellservice.domain.article.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.buysellservice.domain.File.service.FileService;
import project.buysellservice.domain.article.dto.response.ArticleResponse;
import project.buysellservice.domain.article.dto.response.FileResponse;
import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.domain.article.entity.Category;
import project.buysellservice.domain.article.repository.ArticleRepository;
import project.buysellservice.domain.article.service.ArticleService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final FileService fileService;

    // 모든 게시글 가져오기 (최신순)
    @Override
    public List<ArticleResponse> readAllArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAllByOrderByCreatedAtDesc(pageable);

        List<Article> article = articles.getContent();

        return ArticleResponse.listOf(article);
    }

    // 모든 게시글 가져오기 (오래된순)
    @Override
    public List<ArticleResponse> readAllArticlesOrderByAsc(Pageable pageable) {
        Page<Article> articles = articleRepository.findAllByOrderByCreatedAtAsc(pageable);

        List<Article> article = articles.getContent();

        return ArticleResponse.listOf(article);
    }

    // 구매 가능 게시판만 보기
    @Override
    public List<ArticleResponse> readByStateSell(Pageable pageable) {
        Page<Article> articles = articleRepository.findBySellStatus(pageable);

        List<Article> article = articles.getContent();

        return ArticleResponse.listOf(article);
    }

    // 가격대별로 게시판 보기
    @Override
    public List<ArticleResponse> readByPrice(Pageable pageable, int minPrice, int maxPrice) {
        Page<Article> articles = articleRepository.findByPrice(pageable, minPrice, maxPrice);
        List<Article> article = articles.getContent();
        return ArticleResponse.listOf(article);
    }


    // 게시글 팔림 처리
    @Override
    public ArticleResponse soldArticle(Long id) {
        Article article = articleRepository.getByIdOrThrow(id);

        Article soldArticle = Article.soldFrom(article.getId(), article);

        articleRepository.save(soldArticle);

        return ArticleResponse.of(soldArticle);
    }

    // 단일 게시글 가져오기
    @Override
    public ArticleResponse readArticle(Long id) {
        Article article = articleRepository.getByIdOrThrow(id);

        return ArticleResponse.of(article);
    }

    // 카테고리 게시글 가져오기
    @Override
    public List<ArticleResponse> readCategoryArticle(Category category, Pageable pageable) {

        Page<Article> articles = articleRepository.findAllByCategory(category, pageable);

        List<Article> article = articles.getContent();

        return ArticleResponse.listOf(article);
    }

    // 생성
    @Override
    public ArticleResponse createArticle(String name, String content, List<MultipartFile> files, Long price, Category category) throws Exception {
        FileResponse fileData = fileService.createFile(files);

        Article article = Article.createFrom(name, content, fileData.files(), price, fileData.bucketName(), category);

        articleRepository.save(article);

        return ArticleResponse.of(article);
    }

    // 수정
    @Override
    public ArticleResponse updateArticle(Long id, String name, String content, List<MultipartFile> files, Long price, Category category) throws Exception {
        fileService.deleteFile(id);

        FileResponse fileData = fileService.updateFile(files, id);

        Article article = articleRepository.getByIdOrThrow(id);

        Article newArticle = Article.updateFrom(article.getId(), name, content, fileData.files(), price, fileData.bucketName(), category);

        articleRepository.save(newArticle);

        return ArticleResponse.of(newArticle);
    }

    // 삭제
    @Override
    public void deleteArticle(Long id) throws Exception {
        fileService.deleteFile(id);
        articleRepository.deleteById(id);
    }

}
