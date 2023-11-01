package com.backend.dream.rest;


import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.ProductSize;
import com.backend.dream.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/rest/productsizes")
@RestController
public class ProductSizeRestController {
    @Autowired
    private ProductSizeService productSizeService;
    @GetMapping()
    public List<ProductSizeDTO> getAllSizes() {
        return productSizeService.findAll();
    }
    @PostMapping()
    public ProductSize productSize(@RequestBody ProductSizeDTO productSizeDTO) {
        return productSizeService.create(productSizeDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        productSizeService.delete(id);
    }
}
