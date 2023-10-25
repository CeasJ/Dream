package com.backend.dream.service;

import com.backend.dream.dto.OrderDTO;
import com.backend.dream.entity.Orders;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.NoSuchElementException;

public interface OrderService {
    Orders create(JsonNode orderData) throws NoSuchElementException,NullPointerException;
    List<OrderDTO> listOrderByUsername(String username) throws NoSuchElementException;
}
