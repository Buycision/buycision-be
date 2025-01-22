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
public class boardComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body")
    private String body;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public static boardComment from(String body, Board board) {
        return boardComment.builder()
                .board(board)
                .body(body)
                .build();
    }
}