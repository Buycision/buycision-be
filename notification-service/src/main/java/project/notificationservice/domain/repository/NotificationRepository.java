package project.notificationservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.notificationservice.domain.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT * FROM notification n " +
            "WHERE (n.url, n.reg_date)" +
            "IN (SELECT url, max(reg_date) " +
            "FROM notification " +
            "GROUP BY url) " +
            "AND n.receiver_no =:memberNo", nativeQuery = true)
    List<Notification> findByReceiver(Integer memberNo);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notification n SET n.isRead = 1 WHERE n.id = :id")
    void updateIsReadById(Long id);
}
