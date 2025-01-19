package project.communityservice.domain.calender.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import project.communityservice.global.baseEntity.BaseEntity;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(callSuper = true)
@SuperBuilder
public class Calender extends BaseEntity {

    @Id
    private Long id; // 일정 아이디

    private String title; // 일정 제목

    private String content; // 일정 설명

    public static Calender from(String title, String content) {
        return Calender.builder()
                .title(title)
                .content(content)
                .build();
    }
}
