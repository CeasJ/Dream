package com.backend.dream.rest;

import com.backend.dream.dto.CategoryDTO;
import com.backend.dream.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/rest/category")
    public List<CategoryDTO> getAll() throws Exception{
        return categoryService.findAll();
    }
}
