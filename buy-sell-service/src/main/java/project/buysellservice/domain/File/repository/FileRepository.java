package project.buysellservice.domain.File.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.buysellservice.domain.File.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
