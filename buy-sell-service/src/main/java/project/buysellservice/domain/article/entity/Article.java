package project.buysellservice.domain.article.entity;

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

    private String imageUrl; // 사진 (임시) 리스트로 받아오기

    private Long price; // 상품가격

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
