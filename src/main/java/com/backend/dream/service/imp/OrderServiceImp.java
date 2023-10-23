package com.backend.dream.service.imp;

import com.backend.dream.dto.OrderDTO;
import com.backend.dream.dto.OrderDetailDTO;
import com.backend.dream.entity.Orders;
import com.backend.dream.mapper.OrderDetailMapper;
import com.backend.dream.mapper.OrderMapper;
import com.backend.dream.repository.OrderDetailRepository;
import com.backend.dream.repository.OrderRepository;
import com.backend.dream.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Orders create(JsonNode orderData) throws NoSuchElementException, NullPointerException {
        ObjectMapper mapper = new ObjectMapper();

        OrderDTO orderDTO = mapper.convertValue(orderData, OrderDTO.class);

        Orders orders = orderMapper.orderDTOToOrder(orderDTO);

        orderRepository.save(orders);

        TypeReference<List<OrderDetailDTO>> type = new TypeReference<List<OrderDetailDTO>>() {
        };

        List<OrderDetailDTO> details = mapper.convertValue(orderData.get("orderDetails"), type)
                .stream().peek(d -> d.setId_order(orders.getId())).collect(Collectors.toList());

        orderDetailRepository.saveAll(orderDetailMapper.listOrderDetaiDTOlToListOrderDetail(details));

        return orders;
    }
}
