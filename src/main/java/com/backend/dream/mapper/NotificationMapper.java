package com.backend.dream.mapper;

import com.backend.dream.dto.NotificationDTO;
import com.backend.dream.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "account.id", target = "idAccount")
    NotificationDTO notificationToNotificationDTO(Notification notification);

    @Mapping(source = "idAccount", target = "account.id")
    Notification notificationDTOToNotification(NotificationDTO notificationDTO);
}
