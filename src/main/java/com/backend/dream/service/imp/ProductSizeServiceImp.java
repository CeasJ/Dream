package com.backend.dream.service.imp;

import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.dto.SizeDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.entity.ProductSize;
import com.backend.dream.entity.Size;
import com.backend.dream.mapper.ProductSizeMapper;
import com.backend.dream.mapper.SizeMapper;
import com.backend.dream.repository.ProductRepository;
import com.backend.dream.repository.ProductSizeRepository;
import com.backend.dream.repository.SizeRepository;
import com.backend.dream.service.ProductSizeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductSizeServiceImp implements ProductSizeService {
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeMapper productSizeMapper;


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
        List<ProductSize> products = productSizeRepository.findAll();
        return products.stream().map(productSizeMapper::productSizeToProductSizeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductSize create(ProductSizeDTO productSizeDTO) {
        ProductSize saveproductsize = productSizeMapper.productSizeDTOToProductSize(productSizeDTO);
        return productSizeRepository.save(saveproductsize);
    }

    @Override
    public void delete(Long id) {
        productSizeRepository.deleteById(id);
    }

    @Override
    public ProductSizeDTO getProductSizeByProductIdAndSizeId(Long id_product, Long id_size) throws NoSuchElementException {
        ProductSize productSize = productSizeRepository.findProductSizeById(id_product, id_size);

        ProductSizeDTO productSizeDTO = productSizeMapper.productSizeToProductSizeDTO(productSize);

        return productSizeDTO;
    }

    @Override
    public ProductSizeDTO update(ProductSizeDTO productSizeDTO, Long id) {
        Optional<ProductSize> existingProductSize = productSizeRepository.findByProductIdAndSizeId(id,productSizeDTO.getId_size());

        if (existingProductSize.isEmpty()) {
            throw new EntityNotFoundException("ProductSize not foud with id:" + id);
        }

        ProductSize productSize = existingProductSize.get();
        productSize.setPrice(productSizeDTO.getPrice());

        Size size = sizeRepository.findById(productSizeDTO.getId_size()).get();
        productSize.setSize(size);

        Product product = productRepository.findById(productSizeDTO.getId_product()).get();
        productSize.setProduct(product);

        ProductSize updateProductSize = productSizeRepository.save(productSize);


        return productSizeMapper.productSizeToProductSizeDTO(updateProductSize);
    }

}
