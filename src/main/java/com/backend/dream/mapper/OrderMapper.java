package com.backend.dream.mapper;

import com.backend.dream.dto.OrderDTO;
import com.backend.dream.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    @Mapping(source = "status.id", target="status")
    @Mapping(source = "account.id", target="id_account")
    OrderDTO orderToOrderDTO(Orders orders);

    // something wrong here check again
    @Mapping(source = "status", target="status.id")
    @Mapping(source = "id_account", target="account.id")
    Orders orderDTOToOrder(OrderDTO orderDTO);
}
