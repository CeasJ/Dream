package com.backend.dream.mapper;

import com.backend.dream.dto.OrderDetailDTO;
import com.backend.dream.entity.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);
    @Mapping(source = "orders.id",target = "id_order")
    @Mapping(source = "product.id",target = "id_product")
    OrderDetailDTO orderDetailToOrderDetailDTO(OrderDetails orderDetails);
    @Mapping(source = "id_order",target = "orders.id")
    @Mapping(source = "id_product",target = "product.id")
    OrderDetails orderDetailDTOToOrderDetail(OrderDetailDTO orderDetailDTO);
    @Mapping(source = "id_order",target = "order.id")
    @Mapping(source = "id_product",target = "product.id")
    List<OrderDetails> listOrderDetaiDTOlToListOrderDetail(List<OrderDetailDTO> orderDetailDTO);
}
