package com.backend.dream.rest;

import com.backend.dream.dto.CategoryDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rest/category")
@RestController
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public List<CategoryDTO> getAll() throws Exception {
        return categoryService.findAll();
    }


    @PostMapping()
    public CategoryDTO create(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @PutMapping("/update/{id}")
    public CategoryDTO update(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Long id) {
        return categoryService.update(categoryDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }

}
