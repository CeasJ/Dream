package com.backend.dream.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/home")
    public String index(Model model) {
        if (request.getRemoteUser() != null && (request.isUserInRole("ADMIN") || request.isUserInRole("STAFF"))) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("isAdminOrStaff", true);
        }
        return "/user/home/index";
    }

    @RequestMapping("/about")
    public String about() {

    public String about() {
        return "/user/home/about";
    }

    @RequestMapping("/product")
    public String product() {
        return "/user/product/product";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "/user/cart/cart";
    }
}
