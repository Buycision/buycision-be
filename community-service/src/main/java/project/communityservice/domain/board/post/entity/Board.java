package project.communityservice.domain.board.post.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import project.communityservice.domain.board.tag.entity.BoardTag;
import project.communityservice.global.baseEntity.BaseEntity;
import project.communityservice.domain.board.comment.entity.Comment;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_tag_id")
    private BoardTag boardTag; // 게시글 태그

    public static Board createFrom(String title, String content, Long tagId) {
        return Board.builder()
                .title(title)
                .content(content)
                .boardTag(BoardTag.searchFrom(tagId))
                .build();
    }

    public static Board updateFrom(Long id, String title, String content, Long tagId) {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .boardTag(BoardTag.searchFrom(tagId))
                .build();
    }
}
