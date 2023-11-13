package com.backend.dream.rest;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.mapper.DiscountMapper;
import com.backend.dream.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/discount")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private DiscountMapper discountMapper;
    @GetMapping()
    public List<DiscountDTO> getAll() throws Exception {
        return discountService.findAll();
    }
    @PostMapping()
    public DiscountDTO create(@RequestBody DiscountDTO discountDTO) {
        return discountService.createDiscount(discountDTO);
    }
    @PutMapping("{id}")
    public DiscountDTO update(@RequestBody DiscountDTO discountDTO, @PathVariable("id") Long id) {
        return discountService.update(discountDTO);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        discountService.delete(id);
    }
}
