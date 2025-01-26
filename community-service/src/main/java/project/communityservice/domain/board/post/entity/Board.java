package project.communityservice.domain.board.post.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import project.communityservice.domain.board.comment.entity.Comment;
import project.communityservice.domain.board.tag.entity.BoardTag;
import project.communityservice.domain.community.entity.Community;
import project.communityservice.global.baseEntity.BaseEntity;

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
@Builder
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
    private List<Comment> comments = new ArrayList<>(); // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_tag_id")
    private BoardTag boardTag; // 게시글 태그

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = true)
    private Community community; // 커뮤니티

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardType boardType; // 게시글 타입

    // 게시판 생성 빌더
    public static Board createFrom(String title, String content, Long tagId, BoardType boardType) {
        return Board.builder()
                .title(title)
                .content(content)
                .boardTag(BoardTag.searchFrom(tagId))
                .boardType(boardType)
                .build();
    }

    // 게시판 수정 빌더
    public static Board updateFrom(Long id, String title, String content, Long tagId) {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .boardTag(BoardTag.searchFrom(tagId))
                .build();
    }

    // 해당 게시판 찾기
    public static Board searchFrom(Long boardId) {
        return Board.builder()
                .id(boardId)
                .build();
    }
}
