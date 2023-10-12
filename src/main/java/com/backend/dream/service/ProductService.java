package com.backend.dream.service;

import com.backend.dream.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll() throws Exception;
}
