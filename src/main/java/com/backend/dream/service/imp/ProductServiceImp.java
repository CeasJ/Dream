package com.backend.dream.service.imp;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.mapper.ProductMapper;
import com.backend.dream.repository.ProductRepository;
import com.backend.dream.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductServiceImp(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return product != null ? productMapper.productToProductDTO(product) : null;
    }

    @Override
    public List<ProductDTO> findByName(String productName) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(productName);
        return products.stream()
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }


    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        Product createdProduct = productRepository.save(product);
        return productMapper.productToProductDTO(createdProduct);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        Product updatedProduct = productRepository.save(product);
        return productMapper.productToProductDTO(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> findByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryID(categoryId);
        return products.stream()
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::productToProductDTO);
    }

    @Override
    public List<ProductDTO> sortByPriceAsc(Long categoryId) {
        try {
            List<Product> products = productRepository.findByCategoryOrderByPriceAsc(categoryId);
            return products.stream()
                    .map(productMapper::productToProductDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error sorting products by price ascending: " + e.getMessage(), e);
            throw e; // Rethrow the exception to propagate it.
        }
    }

    @Override
    public List<ProductDTO> sortByPriceDesc(Long categoryId) {
        try {
            List<Product> products = productRepository.findByCategoryOrderByPriceDesc(categoryId);
            return products.stream()
                    .map(productMapper::productToProductDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error sorting products by price descending: " + e.getMessage(), e);
            throw e;
        }
    }

}
