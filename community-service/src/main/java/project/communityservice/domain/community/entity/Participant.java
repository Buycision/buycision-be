package project.communityservice.domain.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import project.communityservice.global.baseEntity.BaseEntity;

@Entity
@Getter
public class Participant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

}
