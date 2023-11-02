package com.backend.dream.controller;

import com.backend.dream.dto.OrderDTO;
import com.backend.dream.dto.OrderDetailDTO;
import com.backend.dream.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/orders")
    public String history(Model model) {
        String username = request.getRemoteUser();
        List<OrderDTO> list = orderService.listOrderByUsername(username);
        Collections.sort(list, Comparator.comparing(OrderDTO::getCreateDate));
        for (OrderDTO orderDTO : list) {
            Double totalAmount = 0.0;
            for (OrderDetailDTO detailDTO : orderDTO.getOrderDetailsDTO()) {
                totalAmount += detailDTO.getPrice() * detailDTO.getQuantity();
            }
            orderDTO.setTotalAmount(totalAmount);
        }
        model.addAttribute("listOrder", list);
        return "/user/order/history";
    }

    @GetMapping("/admin/order")
    public String getOrderManagement() {
        return "/admin/home/order";
    }
}
