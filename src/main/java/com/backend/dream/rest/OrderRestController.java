package com.backend.dream.rest;

import com.backend.dream.dto.OrderDTO;
import com.backend.dream.entity.Orders;
import com.backend.dream.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    public Orders create(@RequestBody JsonNode orderData) throws ParseException {
        return orderService.create(orderData);
    }

    @GetMapping()
    public List<OrderDTO> getListOrder() throws ClassNotFoundException {
        List<OrderDTO> listOrder = orderService.getListOrder();
        Collections.sort(listOrder, Comparator.comparing(OrderDTO::getCreateDate));
        return listOrder;
    }
    @GetMapping("/confirm")
    public List<OrderDTO> getListOrderConfirm() throws ClassNotFoundException {
        List<OrderDTO> listOrderConfirm = orderService.getListOrderConfirm();
        Collections.sort(listOrderConfirm, Comparator.comparing(OrderDTO::getCreateDate));
        return listOrderConfirm;
    }
    @GetMapping("/ship")
    public List<OrderDTO> getListOrderIsShipping() throws ClassNotFoundException {
        List<OrderDTO> listOrderIsShipping = orderService.getListOrderIsShipping();
        Collections.sort(listOrderIsShipping, Comparator.comparing(OrderDTO::getCreateDate));
        return listOrderIsShipping;
    }
    @GetMapping("/success")
    public List<OrderDTO> getListOrderSuccess() throws ClassNotFoundException {
        List<OrderDTO> listOrderSuccess = orderService.getListOrderSuccess();
        Collections.sort(listOrderSuccess, Comparator.comparing(OrderDTO::getCreateDate));
        return listOrderSuccess;
    }
    @GetMapping("/cancel")
    public List<OrderDTO> getListOrderCancel() throws ClassNotFoundException {
        List<OrderDTO> listOrderCancel = orderService.getListOrderCancel();
        Collections.sort(listOrderCancel, Comparator.comparing(OrderDTO::getCreateDate));
        return listOrderCancel;
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
