package project.buysellservice.domain.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue
    private Long id; // 고유 아이디

    private String title; // 글 제목

    private String content; // 글 내용

    private Long price; // 가격

    @Column(length = 2048)
    private String imageUrl; // 이미지 url

    public static Article createFrom(String title, String content, String imageUrl, Long price) {
        return Article.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .price(price)
                .build();
    }

    public static Article updateFrom(Long id, String title, String content, String imageUrl, Long price) {
        return Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .price(price)
                .build();
    }
}
