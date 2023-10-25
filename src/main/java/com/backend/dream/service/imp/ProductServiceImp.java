package com.backend.dream.service.imp;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.mapper.ProductMapper;
import com.backend.dream.repository.ProductRepository;
import com.backend.dream.service.DiscountService;
import com.backend.dream.service.ProductService;
import com.backend.dream.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ProductSizeService productSizeService;


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
    public Page<ProductDTO> findByNamePaged(String name, Pageable pageable) {
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(name, pageable);
        return productPage.map(productMapper::productToProductDTO);
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
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::productToProductDTO);
    }

    @Override
    public Page<ProductDTO> findByCategory(Long categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByCategoryID(categoryId, pageable);
        return productPage.map(productMapper::productToProductDTO);
    }

    @Override
    public Page<ProductDTO> sortByPriceAsc(Long categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(categoryId, pageable);
        return productPage.map(productMapper::productToProductDTO);
    }

    @Override
    public Page<ProductDTO> sortByPriceDesc(Long categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceDesc(categoryId, pageable);
        return productPage.map(productMapper::productToProductDTO);
    }

    @Override
    public Page<ProductDTO> findSaleProducts(Pageable pageable) {
        Page<Product> products = productRepository.findSaleProducts(pageable);
        return products.map(productMapper::productToProductDTO);
    }

    @Override
    public double getDiscountedPrice(Long productId) {
        DiscountDTO discount = discountService.getDiscountByProductId(productId);
        double originalPrice = getOriginalProductPrice(productId);

        if (discount != null) {
            double discountPercent = discount.getPercent();
            double discountedPrice = originalPrice - (originalPrice * discountPercent);
            return discountedPrice;
        } else {
            return originalPrice;
        }
    }


    @Override
    public double getOriginalProductPrice(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return product.get().getPrice();
        }
        return 0.0; // Handle the case where the product isn't found
    }


    @Override
    public double getProductPriceBySize(Long productId, Long sizeId) {
        return productRepository.findProductPriceBySize(productId, sizeId);

    }
}
