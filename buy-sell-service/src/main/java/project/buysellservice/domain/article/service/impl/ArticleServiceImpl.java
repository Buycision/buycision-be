package project.buysellservice.domain.article.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.buysellservice.domain.article.dto.response.ArticleResponse;
import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.domain.article.repository.ArticleRepository;
import project.buysellservice.domain.article.service.ArticleService;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    // 생성
    @Override
    public ArticleResponse createArticle(String name, String content, String imageUrl, Long price) {
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
    public ArticleResponse updateArticle(Long id, String name, String content, String imageUrl, Long price) {
        Article article = articleRepository.getByIdOrThrow(id);

        Article newArticle = Article.updateFrom(article.getId(), name, content, imageUrl, price);
        articleRepository.save(newArticle);
        return ArticleResponse.of(newArticle);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

}
