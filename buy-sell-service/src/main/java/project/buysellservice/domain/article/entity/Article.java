package project.buysellservice.domain.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.buysellservice.domain.File.entity.File;
import project.buysellservice.global.Util.BaseEntity;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id; // 고유 아이디

    private String title; // 글 제목

    private String content; // 글 내용

    private Long price; // 가격

    @ElementCollection  // List<String>을 JPA에서 사용할 수 있도록 설정
    @CollectionTable(name = "file_images")  // 별도의 테이블을 생성 (file_images 테이블)
    @Column(name = "image_url")  // 테이블의 컬럼명 지정
    private List<String> imageUrls;

    public static Article createFrom(String title, String content, File file, Long price) {
        return Article.builder()
                .title(title)
                .content(content)
                .file(file)
                .price(price)
                .build();
    }

    public static Article updateFrom(Long id, String title, String content, File file, Long price) {
        return Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .file(file)
                .price(price)
                .build();
    }
}
