package project.communityservice.domain.board.tag.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.global.baseEntity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class BoardTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "tag")
    private String tag; // 태그 이름

    @OneToMany(mappedBy = "boardTag", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    @ToString.Exclude
    private List<Board> board = new ArrayList<>();

    public static BoardTag searchFrom(Long id) {
        return BoardTag.builder()
                .id(id)
                .build();
    }

    public static BoardTag createFrom(String tag) {
        return BoardTag.builder()
                .tag(tag)
                .build();
    }
}
