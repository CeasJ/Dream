package com.backend.dream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/admin")
    public String dashboard() {
        return "/admin/home/index";
    }

    @RequestMapping("/admin/product")
    public String product() {
        return "/admin/home/product";
    }

    @RequestMapping("/admin/authority")
    public String authority() {
        return "/admin/home/authority";
    }

    @RequestMapping("/admin/size")
    public String size() {
        return "/admin/home/size";
    }

    @RequestMapping("/admin/staff")
    public String staff() {
        return "/admin/home/staff";
    }
}
