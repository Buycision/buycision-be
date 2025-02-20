package project.buysellservice.domain.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.buysellservice.global.Util.BaseEntity;

import java.util.List;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id; // 고유 아이디

    @Column(name = "title", nullable = false)
    private String title; // 글 제목

    @Column(name = "content", nullable = false)
    private String content; // 글 내용

    @Column(name = "price", length = 20)
    private Long price; // 가격

    @ElementCollection  // List<String>을 JPA에서 사용할 수 있도록 설정
    @CollectionTable(name = "bucketName")  // 별도의 테이블을 생성 (file_images 테이블)
    @Column(name = "image_url", length = 2048)  // 테이블의 컬럼명 지정
    private List<String> files; // 이미지 저장

    @Column(name = "bucketName")
    private String bucketName; // UUID + Post

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    // 게시글 생성 빌더
    public static Article createFrom(String title, String content, List<String> files, Long price, String bucketName) {
        return Article.builder()
                .title(title)
                .content(content)
                .files(files)
                .price(price)
                .bucketName(bucketName)
                .state(State.SELL)
                .build();
    }

    // 게시글 수정 빌더
    public static Article updateFrom(Long id, String title, String content, List<String> files, Long price, String bucketName) {
        return Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .files(files)
                .price(price)
                .bucketName(bucketName)
                .state(State.SELL)
                .build();
    }

    // 물건이 거래 중 및 팔렸을 때
    public static Article soldFrom(Long id, Article article) {
        return article.toBuilder()
                .id(id)
                .state(State.SOLD)
                .build();
    }
}
