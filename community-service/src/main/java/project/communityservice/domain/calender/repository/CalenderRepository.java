package project.communityservice.domain.calender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.communityservice.domain.calender.entity.Calender;

@Repository
public interface CalenderRepository extends JpaRepository<Calender, Long> {
}
