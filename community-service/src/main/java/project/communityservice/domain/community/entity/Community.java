package project.communityservice.domain.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    @GeneratedValue
    private Long id; // 커뮤니티 아이디

    private String name; // 커뮤니티 이름

    private String description; // 커뮤니티 설명
}
