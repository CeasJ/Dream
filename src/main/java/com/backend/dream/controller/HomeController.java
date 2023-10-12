package com.backend.dream.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/home")
    public String index(Model model) {
        if (request.getRemoteUser() != null) {
            model.addAttribute("isAuthenticated", true);
            if (request.isUserInRole("ADMIN") || request.isUserInRole("STAFF")) {
                model.addAttribute("isAdminOrStaff", true);
            }
        }
        return "/user/home/index";
    }

    @GetMapping("/about")
    public String about() {
        return "/user/home/about";
    }

    @GetMapping("/product")
    public String product() {
        return "/user/product/product";
    }

    @GetMapping("/cart")
    public String cart() {
        return "/user/cart/cart";
    }
}
