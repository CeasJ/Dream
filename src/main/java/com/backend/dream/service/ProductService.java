package com.backend.dream.service;

import com.backend.dream.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();

    ProductDTO findById(Long id);

    List<ProductDTO> findByName(String name);

    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    List<ProductDTO> sortByPriceAsc(Long categoryId);
    List<ProductDTO> sortByPriceDesc(Long categoryId);


    void delete(Long id);

    List<ProductDTO> findByCategory(Long categoryId);

    Page<ProductDTO> findAll(Pageable pageable);
}
