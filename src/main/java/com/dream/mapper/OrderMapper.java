package com.dream.mapper;

import com.dream.dto.OrderDTO;
import com.dream.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    @Mapping(source = "status.name", target="status")
    OrderDTO orderToOrderDTO(Order order);

    // something wrong here check again
    @Mapping(source = "status", target="status.name")
    Order orderDTOToOrder(OrderDTO orderDTO);

}
