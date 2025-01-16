package project.communityservice.domain.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import project.communityservice.global.baseEntity.BaseEntity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Entity
@ToString(callSuper = true)
@SuperBuilder
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id; // 게시글 아이디

    private String title; // 게시글 제목

    private String content; // 게시글 내용
}
