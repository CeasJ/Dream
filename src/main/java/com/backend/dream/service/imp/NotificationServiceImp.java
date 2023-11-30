package com.backend.dream.service.imp;

import com.backend.dream.dto.NotificationDTO;
import com.backend.dream.entity.Notification;
import com.backend.dream.mapper.NotificationMapper;
import com.backend.dream.repository.NotificationRepository;
import com.backend.dream.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImp implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationServiceImp(NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }
    @Override
    public List<NotificationDTO> getNotificationsByAccountId(Long idAccount) {
        List<Notification> notifications = notificationRepository.findAllNotifications(idAccount);
        return notifications.stream()
                .map(notificationMapper::notificationToNotificationDTO)
                .collect(Collectors.toList());
    }
}
