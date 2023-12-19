package com.backend.dream.service;

import com.backend.dream.dto.OrderDTO;
import com.backend.dream.entity.Orders;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

public interface OrderService {
    Orders create(JsonNode orderData) throws NoSuchElementException, NullPointerException, ParseException;
    List<OrderDTO> listOrderByUsername(String username) throws NoSuchElementException;
    List<OrderDTO> getListOrder() throws ClassNotFoundException;
    List<OrderDTO> getListOrderConfirm() throws ClassNotFoundException;
    List<OrderDTO> getListOrderIsShipping() throws ClassNotFoundException;
    List<OrderDTO> getListOrderSuccess() throws ClassNotFoundException;
    List<OrderDTO> getListOrderCancel() throws ClassNotFoundException;
    OrderDTO updateOrder(OrderDTO orderDTO) throws ClassNotFoundException,NoSuchElementException;

    ByteArrayInputStream getdataOrder() throws IOException;
}
