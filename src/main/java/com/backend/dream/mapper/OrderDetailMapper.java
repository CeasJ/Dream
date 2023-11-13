package com.backend.dream.mapper;

import com.backend.dream.dto.*;
import com.backend.dream.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);
    @Mapping(source = "orders.id",target = "id_order")
    @Mapping(source = "product.id",target = "id_product")
    @Mapping(source = "product.image",target = "product_image")
    @Mapping(source = "product.name",target = "product_name")
    @Mapping(source = "orders.account.fullname",target = "account_fullname")
    @Mapping(source = "orders.account.phone",target = "account_phone")
    @Mapping(source = "orders.createDate",target = "order_createDate")
    @Mapping(source = "orders.address",target = "order_address")
    @Mapping(source = "orders.status.id",target = "order_status")
    OrderDetailDTO orderDetailToOrderDetailDTO(OrderDetails orderDetails);

    @Mapping(source = "id_order",target = "orders.id")
    @Mapping(source = "id_product",target = "product.id")
    OrderDetails orderDetailDTOToOrderDetail(OrderDetailDTO orderDetailDTO);
    
    @Mapping(source = "id_order",target = "order.id")
    @Mapping(source = "id_product",target = "product.id")
    List<OrderDetails> listOrderDetaiDTOlToListOrderDetail(List<OrderDetailDTO> orderDetailDTO);

    @Mapping(source = "orders.id",target = "id_order")
    @Mapping(source = "product.id",target = "id_product")
    @Mapping(source = "product.image",target = "product_image")
    @Mapping(source = "product.name",target = "product_name")
    @Mapping(source = "orders.account.fullname",target = "account_fullname")
    @Mapping(source = "orders.account.phone",target = "account_phone")
    @Mapping(source = "orders.createDate",target = "order_createDate")
    @Mapping(source = "orders.address",target = "order_address")
    @Mapping(source = "orders.status.id",target = "order_status")
    List<OrderDetailDTO> listOrderDetailToListOrderDetailDTO(List<OrderDetails> orderDetails);

}
