package com.backend.dream.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/home")
    public String index() {
        return "/user/home/index";
    }

    @RequestMapping("/about")
    public String about() {
        return "/user/home/about";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "/user/cart/cart";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "/user/infor/profile";
    }

    @RequestMapping("/updatePasswordSuccess")
    public String updatePasswordSuccess() {
        return "/user/infor/updatePassSuccess";
    }
    @RequestMapping("/changePassword")
    public String changePassword() {
        return "/user/infor/changePassword";
    }

    @RequestMapping("/voucher")
    public String voucher() {
        return "/user/voucher/voucher";
    }

}
