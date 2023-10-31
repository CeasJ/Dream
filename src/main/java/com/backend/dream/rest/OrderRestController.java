package com.backend.dream.rest;

import com.backend.dream.dto.OrderDTO;
import com.backend.dream.entity.Orders;
import com.backend.dream.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @GetMapping()
    public List<OrderDTO> getListOrder() throws ClassNotFoundException {
        List<OrderDTO> getListOrder = orderService.getListOrder();
        Collections.sort(getListOrder, Comparator.comparing(OrderDTO::getCreateDate));
        return getListOrder;
    }
    @GetMapping("/confirm")
    public List<OrderDTO> getListOrderConfirm() throws ClassNotFoundException {
        List<OrderDTO> getListOrderConfirm = orderService.getListOrderConfirm();
        Collections.sort(getListOrderConfirm, Comparator.comparing(OrderDTO::getCreateDate));
        return getListOrderConfirm;
    }
    @GetMapping("/ship")
    public List<OrderDTO> getListOrderIsShipping() throws ClassNotFoundException {
        List<OrderDTO> getListOrderIsShipping = orderService.getListOrderIsShipping();
        Collections.sort(getListOrderIsShipping, Comparator.comparing(OrderDTO::getCreateDate));
        return getListOrderIsShipping;
    }
    @GetMapping("/success")
    public List<OrderDTO> getListOrderSuccess() throws ClassNotFoundException {
        List<OrderDTO> getListOrderSuccess = orderService.getListOrderSuccess();
        Collections.sort(getListOrderSuccess, Comparator.comparing(OrderDTO::getCreateDate));
        return getListOrderSuccess;
    }
    @GetMapping("/cancel")
    public List<OrderDTO> getListOrderCancel() throws ClassNotFoundException {
        List<OrderDTO> getListOrderCancel = orderService.getListOrderCancel();
        Collections.sort(getListOrderCancel, Comparator.comparing(OrderDTO::getCreateDate));
        return getListOrderCancel;
    }
    @PutMapping("/{id}")
    public OrderDTO changeToConfirmStatus(@PathVariable("id") Long id,@RequestBody OrderDTO orderDTO) throws ClassNotFoundException {
        return orderService.updateOrder(orderDTO);
    }
    @PutMapping("/ship/{id}")
    public OrderDTO changeOrderShipStatus(@PathVariable("id") Long id,@RequestBody OrderDTO orderDTO) throws ClassNotFoundException {
        return orderService.updateOrder(orderDTO);
    }
    @PutMapping("/success/{id}")
    public OrderDTO changeOrderSuccessStatus(@PathVariable("id") Long id,@RequestBody OrderDTO orderDTO) throws ClassNotFoundException {
        return orderService.updateOrder(orderDTO);
    }
    @PutMapping("/cancel/{id}")
    public OrderDTO changeOrderCancelStatus(@PathVariable("id") Long id,@RequestBody OrderDTO orderDTO) throws ClassNotFoundException {
        return orderService.updateOrder(orderDTO);
    }
    @PutMapping("/reset/{id}")
    public OrderDTO resetOrderStatus(@PathVariable("id") Long id,@RequestBody OrderDTO orderDTO) throws ClassNotFoundException {
        return orderService.updateOrder(orderDTO);
    }

}
