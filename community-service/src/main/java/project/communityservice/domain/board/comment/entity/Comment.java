package project.communityservice.domain.board.comment.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.global.baseEntity.BaseEntity;

import static lombok.AccessLevel.PROTECTED;


@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "board_comment")
@SuperBuilder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 해당 댓글

    @Column(name = "body")
    private String body; // 댓글 내용

//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    @Builder.Default
//    private Board board; // 해당 게시판 조회

    // 댓글 빌더
    public static Comment from(String body) {
        return Comment.builder()
                .body(body)
                .build();
    }
}