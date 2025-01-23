package project.communityservice.domain.board.tag.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.global.baseEntity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class boardTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "tags")
    private String tags; // 태그 이름

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id")
//    @Builder.Default
//    private Board board;

    public boardTag from (String tags) {
        return boardTag.builder()
                .tags(tags)
                .build();
    }

}
