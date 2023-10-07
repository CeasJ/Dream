package com.backend.dream.service;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Long id);

    List<Product> findbyName(String name);

    Product create(Product product);

    Product update(Product product);

    void delete(Long id);

    List<Product> findByCategory(Long categoryId);

    Page<ProductDTO> findAll(Pageable pageable);
}
