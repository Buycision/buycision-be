package project.buysellservice.domain.File.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.buysellservice.domain.article.entity.Article;
import project.buysellservice.global.Util.BaseEntity;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection  // List<String>을 JPA에서 사용할 수 있도록 설정
    @CollectionTable(name = "file_images")  // 별도의 테이블을 생성 (file_images 테이블)
    @Column(name = "image_url")  // 테이블의 컬럼명 지정
    private List<String> imageUrls;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private Article article;

    private String bucketName;

    public static File createFrom(List<String> files, String bucketName) {
        return File.builder()
                .imageUrls(files)
                .bucketName(bucketName)
                .build();
    }
}
