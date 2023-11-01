package com.backend.dream.service;

import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.dto.SizeDTO;
import com.backend.dream.entity.Authority;
import com.backend.dream.entity.ProductSize;

import java.util.List;

public interface ProductSizeService {
    List<SizeDTO> getSizesByProductId(Long productId);

    List<ProductSizeDTO> findAll();

    ProductSize create(ProductSizeDTO productSizeDTO);

    void delete(Long id);

}
