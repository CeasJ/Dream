package com.backend.dream.service.imp;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.entity.ProductSize;
import com.backend.dream.mapper.ProductMapper;
import com.backend.dream.repository.ProductRepository;
import com.backend.dream.repository.ProductSizeRepository;
import com.backend.dream.service.DiscountService;
import com.backend.dream.service.FeedbackService;
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

    private final ProductSizeRepository productSizeRepository;


    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ProductSizeService productSizeService;

    @Autowired
    private FeedbackService feedbackService;


    @Autowired
    public ProductServiceImp(ProductRepository productRepository, ProductMapper productMapper, ProductSizeRepository productSizeRepository  ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productSizeRepository = productSizeRepository;

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
        Product product = productRepository.getReferenceById(id);
        return product != null ? productMapper.productToProductDTO(product) : null;
    }


    @Override
    public Page<ProductDTO> findByNamePaged(String name, Pageable pageable) {
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(name, pageable);
        return productPage.map(product -> {
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            productDTO.setAverageRating(feedbackService.getAverageRating(product.getId()));
            return productDTO;
        });
    }


    @Override
    public Product create(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        return productRepository.save(product);
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
    public Page<ProductDTO> findByCategory(Long categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByCategoryID(categoryId, pageable);
        return productPage.map(product -> {
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            productDTO.setAverageRating(feedbackService.getAverageRating(product.getId()));
            return productDTO;
        });
    }

    @Override
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::productToProductDTO);
    }


    @Override
    public Page<ProductDTO> sortByPriceAsc(Long categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(categoryId, pageable);
        return productPage.map(product -> {
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            productDTO.setAverageRating(feedbackService.getAverageRating(product.getId()));
            return productDTO;
        });
    }

    @Override
    public Page<ProductDTO> sortByPriceDesc(Long categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceDesc(categoryId, pageable);
        return productPage.map(product -> {
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            productDTO.setAverageRating(feedbackService.getAverageRating(product.getId()));
            return productDTO;
        });
    }

    @Override
    public Page<ProductDTO> findSaleProducts(Pageable pageable) {
        Page<Product> products = productRepository.findSaleProducts(pageable);
        return products.map(product -> {
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            productDTO.setAverageRating(feedbackService.getAverageRating(product.getId()));
            return productDTO;
        });
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
        return 0.0;
    }

    @Override
    public double getProductPriceBySize(Long productId, Long sizeId) {
        double originalPrice = getOriginalProductPrice(productId);

        // Fetch the size-specific price from the repository
        Optional<ProductSize> productSize = productSizeRepository.findByProductIdAndSizeId(productId, sizeId);

        if (productSize.isPresent()) {
            double sizeSpecificPrice = productSize.get().getPrice();
            DiscountDTO discount = discountService.getDiscountByProductId(productId);

            if (discount != null) {
                double discountPercent = discount.getPercent();
                double discountedPrice = sizeSpecificPrice - (sizeSpecificPrice * discountPercent);
                return discountedPrice;
            } else {
                return sizeSpecificPrice;
            }
        } else {
            return originalPrice;
        }
    }

    public double getDiscountPercentByProductId(Long productId) {
        DiscountDTO discountDTO = discountService.getDiscountByProductId(productId);
        if (discountDTO != null) {
            return discountDTO.getPercent();
        }
        return 0.0;
    }


    @Override
    public Page<ProductDTO> findByTopRated(Long categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByTopRating(categoryId, pageable);
        return productPage.map(product -> {
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            productDTO.setAverageRating(feedbackService.getAverageRating(product.getId()));
            return productDTO;
        });
    }

    @Override
    public Page<ProductDTO> findByBestSeller(Long categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByBestseller(categoryId, pageable);
        return productPage.map(product -> {
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            productDTO.setAverageRating(feedbackService.getAverageRating(product.getId()));
            return productDTO;
        });
    }
}
