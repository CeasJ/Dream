package com.backend.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }
    @GetMapping("/cart")
    public String getCart(){
        return "cart";
    }
    @GetMapping("/coffee")
    public String getCoffeeShop(){
        return "coffee";
    }
    @GetMapping("/tea")
    public String getTeaShop(){
        return "tea";
    }
    @GetMapping("/dessert")
    public String getDessertShop(){
        return "dessert";
    }
    @GetMapping("/sale")
    public String getSaleShop(){
        return "product-sale";
    }
}
