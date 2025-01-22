package project.communityservice.domain.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.community.entity.Community;
import project.communityservice.domain.community.entity.Participant;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    // 가입한 커뮤니티 확인
    @Query("SELECT c FROM Community c JOIN c.participant p WHERE p.id = :id")
    Page<Community> findAllByParticipant(
            @Param("participants") Long id,
            Pageable pageable
    );
}
