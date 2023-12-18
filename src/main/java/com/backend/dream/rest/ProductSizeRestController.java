package com.backend.dream.rest;


import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.ProductSize;
import com.backend.dream.service.ProductSizeService;
import com.backend.dream.util.ValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/rest/productsizes")
@RestController
public class ProductSizeRestController {
    @Autowired
    private ProductSizeService productSizeService;
    @Autowired
    private ValidationService validateService;
    @GetMapping()
    public List<ProductSizeDTO> getAllSizes() {
        return productSizeService.findAll();
    }
    @PostMapping()
    public ResponseEntity<?> productSize(@RequestBody @Valid ProductSizeDTO productSizeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            validateService.validation(bindingResult);
            return ResponseEntity.badRequest().body(validateService.validation(bindingResult));
        }

        return ResponseEntity.ok(productSizeService.create(productSizeDTO));
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        productSizeService.delete(id);
    }
    @PutMapping("{id}")
    public ProductSizeDTO productSizeDTO(@RequestBody ProductSizeDTO productSizeDTO,@PathVariable("id") Long id){
        return productSizeService.update(productSizeDTO,id);
    }
}
