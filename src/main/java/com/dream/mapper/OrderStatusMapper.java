package com.dream.mapper;

import com.dream.dto.OrderStatusDTO;
import com.dream.entity.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {
    OrderStatusMapper INSTANCE = Mappers.getMapper(OrderStatusMapper.class);

    OrderStatusDTO orderStatusToOrderStatusDTO(OrderStatus orderStatus);
    OrderStatus orderStatusDTOToOrderStatus(OrderStatusDTO orderStatusDTO);

}
