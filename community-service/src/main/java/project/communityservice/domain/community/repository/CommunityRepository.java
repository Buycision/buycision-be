package project.communityservice.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.community.entity.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    default Community getByIdOrThrow(Long id){
        return findById(id).orElseThrow();
    }

}
