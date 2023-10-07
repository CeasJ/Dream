package com.backend.dream.service;

import com.backend.dream.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category create(Category category);
    Category update(Category category);
    void delete(Long id);
}
