package com.backend.dream.service;

import com.backend.dream.entity.Orders;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.NoSuchElementException;

public interface OrderService {
    Orders create(JsonNode orderData) throws NoSuchElementException,NullPointerException;
}
