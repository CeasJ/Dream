package com.backend.dream.rest;

import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductSizeRestController {
    @Autowired
    private ProductSizeService productSizeService;
    @GetMapping("/rest/productsizes")
    public List<ProductSizeDTO> getAllSizes() {

        return productSizeService.findAll();
    }

}
