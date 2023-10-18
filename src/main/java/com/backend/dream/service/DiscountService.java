package com.backend.dream.service;

import com.backend.dream.dto.DiscountDTO;

public interface DiscountService {
    DiscountDTO createDiscount(DiscountDTO discount);
    DiscountDTO getDiscountByProductId(Long idProduct);
    void deleteDiscount(Long discountId);
}
