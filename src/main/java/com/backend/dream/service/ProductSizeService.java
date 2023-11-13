package com.backend.dream.service;

import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.dto.SizeDTO;
import com.backend.dream.entity.ProductSize;

import java.util.List;
import java.util.NoSuchElementException;

public interface ProductSizeService {
    List<SizeDTO> getSizesByProductId(Long productId);

    List<ProductSizeDTO> findAll();

    ProductSize create(ProductSizeDTO productSizeDTO);

    void delete(Long id);

    ProductSizeDTO getProductSizeByProductIdAndSizeId(Long id_product, Long id_size) throws NoSuchElementException;

}
