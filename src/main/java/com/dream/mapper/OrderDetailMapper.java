package com.dream.mapper;

import com.dream.dto.OrderDetailDTO;
import com.dream.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    @Mapping(source = "order.id",target = "id_order")
    @Mapping(source = "product.id",target = "id_product")
    OrderDetailDTO orderDetailToOrderDetailDTO(OrderDetail orderDetail);
    @Mapping(source = "id_order",target = "order.id")
    @Mapping(source = "id_product",target = "product.id")
    OrderDetail orderDetailDTOToOrderDetail(OrderDetailDTO orderDetailDTO);


}
