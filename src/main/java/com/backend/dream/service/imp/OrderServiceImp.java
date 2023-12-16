package com.backend.dream.service.imp;

import com.backend.dream.dto.OrderDTO;
import com.backend.dream.dto.OrderDetailDTO;
import com.backend.dream.entity.Orders;
import com.backend.dream.entity.Voucher;
import com.backend.dream.mapper.OrderDetailMapper;
import com.backend.dream.mapper.OrderMapper;
import com.backend.dream.repository.OrderDetailRepository;
import com.backend.dream.repository.OrderRepository;
import com.backend.dream.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
    private static final int statusOrder = 1;
    private static final int statusOrderConfirm = 2;
    private static final int statusOrderShip = 3;
    private static final int statusOrderSuccess = 4;
    private static final int statusOrderCancel = 5;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Orders create(JsonNode orderData) throws NoSuchElementException, NullPointerException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        OrderDTO orderDTO = mapper.convertValue(orderData, OrderDTO.class);


        Orders orders = orderMapper.orderDTOToOrder(orderDTO);
        if (orderDTO.getId_voucher() == null){
            orders.setVoucher(null);
        }

        orderRepository.save(orders);

        TypeReference<List<OrderDetailDTO>> type = new TypeReference<List<OrderDetailDTO>>() {
        };

        List<OrderDetailDTO> details = mapper.convertValue(orderData.get("orderDetails"), type)
                .stream().peek(d -> d.setId_order(orders.getId())).collect(Collectors.toList());

        orderDetailRepository.saveAll(orderDetailMapper.listOrderDetaiDTOlToListOrderDetail(details));

        return orders;
    }

    @Override
    public Page<OrderDTO> listOrderByUsername(String username, Pageable pageable) throws NoSuchElementException {
        Page<Orders> ordersPage = orderRepository.listOrdersByUsername(username, pageable);
        List<OrderDTO> listOrders = orderMapper.listOrderToListOrderDTO(ordersPage.getContent());
        return new PageImpl<>(listOrders, pageable, ordersPage.getTotalElements());
    }


    @Override
    public List<OrderDTO> getListOrder() throws ClassNotFoundException {
        List<OrderDTO> listOrder = orderMapper.listOrderToListOrderDTO(orderRepository.getListOrder(statusOrder));
        return listOrder;
    }

    @Override
    public List<OrderDTO> getListOrderConfirm() throws ClassNotFoundException {
        List<OrderDTO> listOrder = orderMapper.listOrderToListOrderDTO(orderRepository.getListOrder(statusOrderConfirm));
        return listOrder;
    }

    @Override
    public List<OrderDTO> getListOrderIsShipping() throws ClassNotFoundException {
        List<OrderDTO> listOrder = orderMapper.listOrderToListOrderDTO(orderRepository.getListOrder(statusOrderShip));
        return listOrder;
    }

    @Override
    public List<OrderDTO> getListOrderSuccess() throws ClassNotFoundException {
        List<OrderDTO> listOrder = orderMapper.listOrderToListOrderDTO(orderRepository.getListOrder(statusOrderSuccess));
        return listOrder;
    }

    @Override
    public List<OrderDTO> getListOrderCancel() throws ClassNotFoundException {
        List<OrderDTO> listOrder = orderMapper.listOrderToListOrderDTO(orderRepository.getListOrder(statusOrderCancel));
        return listOrder;
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) throws ClassNotFoundException, NoSuchElementException {
        Orders orders = orderMapper.orderDTOToOrder(orderDTO);
        if (orderDTO.getId_voucher() == null){
            orders.setVoucher(null);
        }
        Orders updateOrder = orderRepository.save(orders);
        return orderMapper.orderToOrderDTO(updateOrder);
    }
}
