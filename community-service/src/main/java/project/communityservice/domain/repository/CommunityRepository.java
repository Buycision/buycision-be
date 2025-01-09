package project.communityservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.entity.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
}
