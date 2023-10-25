package com.backend.dream.rest;

import com.backend.dream.entity.Orders;
import com.backend.dream.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/order")
public class OrderRestController {
    @Autowired
    OrderService orderService;

    @PostMapping
    public Orders create(@RequestBody JsonNode orderData){
        return orderService.create(orderData);
    }
}
