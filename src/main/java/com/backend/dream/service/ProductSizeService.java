package com.backend.dream.service;

import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.dto.SizeDTO;
import java.util.List;

public interface ProductSizeService {
    List<SizeDTO> getSizesByProductId(Long productId);


}

