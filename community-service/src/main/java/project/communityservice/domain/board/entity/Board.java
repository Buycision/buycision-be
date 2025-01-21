package project.communityservice.domain.board.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import project.communityservice.global.baseEntity.BaseEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "board")
@SuperBuilder
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id; // 게시글 아이디

    @Column(nullable = false, name = "title")
    private String title; // 게시글 제목

    @Column(nullable = false, name = "content")
    private String content; // 게시글 내용

    public static Board createFrom(String title, String content) {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }

    public static Board updateFrom(Long id, String title, String content) {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }
}
