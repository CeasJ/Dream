package com.backend.dream.controller;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/store")
    public String store(Model model) throws Exception {
        List<ProductDTO> allProducts = productService.findAll();
        model.addAttribute("list", allProducts);
        return "/user/product/store";
    }

}
