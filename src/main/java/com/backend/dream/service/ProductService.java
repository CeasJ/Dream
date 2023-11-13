package com.backend.dream.service;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDTO findById(Long id);

    // Page<ProductDTO> findByName(String productName, Pageable pageable);

    Page<ProductDTO> findByNamePaged(String name, Pageable pageable);

    Product create(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    Page<ProductDTO> findByCategory(Long categoryId, Pageable pageable);

    Page<ProductDTO> sortByPriceAsc(Long categoryId, Pageable pageable);

    Page<ProductDTO> sortByPriceDesc(Long categoryId, Pageable pageable);
    void delete(Long id);

    Page<ProductDTO> findSaleProducts(Pageable pageable);

    //  Display discounted price and original price in product detail and product list
    double getDiscountedPrice(Long productId);

    double getOriginalProductPrice(Long productId);

    double getProductPriceBySize(Long productId, Long sizeId);

    double getDiscountPercentByProductId(Long productId);


}
