package project.communityservice.domain.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.community.entity.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    default Community getByIdOrThrow(Long id){
        return findById(id).orElseThrow();
    }

    // 전체 커뮤니티 확인
    @Query("SELECT c FROM Community c")
    Page<Community> findAll(Pageable pageable);

    // 본인이 만든 커뮤니티 확인
    @Query("SELECT c FROM Community c WHERE c.host = :host")
    Page<Community> findAllByHost(
            @Param("host") Long host,
            Pageable pageable
    );

    // 가입한 커뮤니티 확인
    @Query("SELECT c FROM Community c WHERE c.participants = :participants")
    Page<Community> findAllByParticipants(
            @Param("participants") Long participants,
            Pageable pageable
    );

}
