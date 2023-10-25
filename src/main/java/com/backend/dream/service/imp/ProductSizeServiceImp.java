package com.backend.dream.service.imp;

import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.dto.SizeDTO;
import com.backend.dream.entity.ProductSize;
import com.backend.dream.mapper.ProductSizeMapper;
import com.backend.dream.mapper.SizeMapper;
import com.backend.dream.repository.ProductSizeRepository;
import com.backend.dream.repository.SizeRepository;
import com.backend.dream.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSizeServiceImp implements ProductSizeService {
    private final ProductSizeRepository productSizeRepository;
    private final SizeRepository sizeRepository;
    @Autowired
    private ProductSizeMapper productSizeMapper;

    @Autowired
    public ProductSizeServiceImp(ProductSizeRepository productSizeRepository, SizeRepository sizeRepository) {
        this.productSizeRepository = productSizeRepository;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public List<SizeDTO> getSizesByProductId(Long productId) {
        List<ProductSize> productSizes = productSizeRepository.findAllByProductId(productId);
        List<SizeDTO> sizeDTOs = productSizes.stream()
                .map(productSize -> SizeMapper.INSTANCE.sizeToSizeDTO(productSize.getSize()))
                .collect(Collectors.toList());

        return sizeDTOs;
    }

    @Override
    public List<ProductSizeDTO> findAll() {
        List<ProductSize> productSizes = productSizeRepository.findAll();
        return productSizes.stream()
                .map(productSizeMapper::productSizeToProductSizeDTO)
                .collect(Collectors.toList());
    }


}
