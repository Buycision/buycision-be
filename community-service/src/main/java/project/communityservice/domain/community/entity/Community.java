package project.communityservice.domain.community.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode
@SuperBuilder
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id; // 커뮤니티 아이디

    @Column(name = "host_id")
    private Long host;

    @Column(name = "participant_id")
    private Long participants;

    @Column(name = "board_name")
    private String name; // 커뮤니티 이름

    @Column(name = "board_descrption")
    private String description; // 커뮤니티 설명

//    @OneToMany
//    private Board board;
//
//    @OneToMany
//    private Calender calender;

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

    // 커뮤니티 참가
    public static Community participant(Long id, Long participants) {
        return Community.builder()
                .id(id)
                .participants(participants)
                .build();
    }
}
