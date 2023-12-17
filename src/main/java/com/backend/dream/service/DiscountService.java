package com.backend.dream.service;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Discount;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface DiscountService {
    DiscountDTO createDiscount(DiscountDTO discount);
    DiscountDTO getDiscountByCategoryId(Long id);
    Double getDiscountPercentByProductId(Long idProduct);
//    void deleteDiscount(Long discountId);
    List<DiscountDTO> findAll();
    DiscountDTO update(DiscountDTO discountDTO);
    void delete(Long id);

    ByteArrayInputStream getdataDiscount() throws IOException;

}
