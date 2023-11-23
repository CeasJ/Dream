package com.backend.dream.repository;

import com.backend.dream.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.account.id = :idAccount ORDER BY n.createdTime DESC LIMIT 5")
    List<Notification> findAllNotifications(@Param("idAccount") Long idAccount);
}
