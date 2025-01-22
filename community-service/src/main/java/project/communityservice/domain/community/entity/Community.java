package project.communityservice.domain.community.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import project.communityservice.domain.board.post.entity.Board;
import project.communityservice.domain.calender.entity.Calender;
import project.communityservice.global.baseEntity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(callSuper = true)
@SuperBuilder
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id; // 커뮤니티 아이디

    @Column(name = "host_id")
    private Long host;

    @Column(name = "board_name")
    private String name; // 커뮤니티 이름

    @Column(name = "board_descrption")
    private String description; // 커뮤니티 설명

//    @OneToMany(mappedBy = "Board", orphanRemoval = true, cascade = CascadeType.ALL)
//    @ToString.Exclude
//    private List<Board> board;
//
//    @OneToMany(mappedBy = "Calender", orphanRemoval = true, cascade = CascadeType.ALL)
//    @ToString.Exclude
//    private List<Calender> calender;


    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Participant> participant = new ArrayList<>();

    // 커뮤니티 생성
    public static Community createFrom(String name, String description) {
        return Community.builder()
                .name(name)
                .description(description)
                .build();
    }

    // 커뮤니티 수정
    public static Community updateFrom(Long id, String name, String description) {
        return Community.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }

}
