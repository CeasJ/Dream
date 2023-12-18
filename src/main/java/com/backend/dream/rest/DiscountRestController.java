package com.backend.dream.rest;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.mapper.DiscountMapper;
import com.backend.dream.service.DiscountService;
import com.backend.dream.util.ValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/discount")
public class DiscountRestController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private ValidationService validateService;
    @GetMapping()
    public List<DiscountDTO> getAll() throws Exception {
        return discountService.findAll();
    }
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid DiscountDTO discountDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            validateService.validation(bindingResult);
            return ResponseEntity.badRequest().body(validateService.validation(bindingResult));
        }

        return ResponseEntity.ok(discountService.createDiscount(discountDTO));
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
