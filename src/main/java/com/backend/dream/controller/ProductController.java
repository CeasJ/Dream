package com.backend.dream.controller;

import com.backend.dream.dto.*;
import com.backend.dream.entity.Account;
import com.backend.dream.entity.Product;
import com.backend.dream.mapper.AccountMapper;
import com.backend.dream.mapper.ProductMapper;
import com.backend.dream.repository.FeedBackRepository;
import com.backend.dream.repository.ProductRepository;
import com.backend.dream.service.DiscountService;
import com.backend.dream.service.FeedbackService;
import com.backend.dream.service.ProductService;
import com.backend.dream.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class ProductController {

    private Long defaultCategoryId = 1L;
    private final ProductService productService;
    private final ProductSizeService productSizeService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    DiscountService discountService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    public ProductController(ProductService productService, ProductSizeService productSizeService) {
        this.productService = productService;
        this.productSizeService = productSizeService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productRepository.save(
                productMapper.productDTOToProduct(productDTO)), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProductDTO> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(productMapper.productToProductDTO(productRepository.findById(id).get()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProductDTO> deleteById(@PathVariable(value = "id") Long id) {
        ProductDTO productDTO = productMapper.productToProductDTO(productRepository.findById(id).get());
        productRepository.deleteById(productDTO.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/product")
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "3") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductDTO> productPage = productService.findAll(pageable);
        List<ProductDTO> productDTOs = productPage.getContent();
        model.addAttribute("products", productDTOs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "user/product/store";
    }

    @GetMapping("/store")
    public String showListProducts(
            @RequestParam(required = false) String sortOption,
            @RequestParam(name = "categoryId", required = false) String categoryIdString,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        int pageSize = 6; // Kích thước trang
        Pageable pageable = PageRequest.of(page, pageSize); // Tạo Pageable

        Page<ProductDTO> productPage;
        Long categoryIdValue;
        if (categoryIdString != null && !categoryIdString.isEmpty()) {
            categoryIdValue = Long.parseLong(categoryIdString);
        } else {
            categoryIdValue = defaultCategoryId;
        }

        if ("asc".equals(sortOption)) {
            // Sắp xếp theo giá tăng dần
            productPage = productService.sortByPriceAsc(categoryIdValue, pageable);
        } else if ("desc".equals(sortOption)) {
            // Sắp xếp theo giá giảm dần
            productPage = productService.sortByPriceDesc(categoryIdValue, pageable);
        } else if ("sale".equals(sortOption)) {
            productPage = productService.findSaleProducts(pageable);
        } else {
            // Mặc định
            productPage = productService.findByCategory(categoryIdValue, pageable);
        }


        List<ProductDTO> products = productPage.getContent();
        for (ProductDTO product : products) {
            double discountedPrice = productService.getDiscountedPrice(product.getId());
            if (discountedPrice < product.getPrice()) {
                product.setIsDiscounted(true);
                product.setDiscountedPrice(discountedPrice);
            } else {
                product.setIsDiscounted(false);
            }
        }

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("categoryId", categoryIdValue);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "user/product/products-list";
    }



    @GetMapping("/search")
    public String searchByName(@RequestParam String productName,
                               @RequestParam(defaultValue = "0") int page,
                               Model model) {
        int pageSize = 6;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<ProductDTO> productPage = productService.findByNamePaged(productName, pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("searchValue", productName); // Add this line to pass search value to the view

        return "user/product/products-list";
    }

    @RequestMapping(value = "/product/{name}", method = RequestMethod.GET)
    public String productDetail(@PathVariable(value = "name") String name,
                                @RequestParam(value = "sizeId", required = false) Long sizeId,
                                @RequestParam(value = "starRating", required = false, defaultValue = "0") int starRating,
                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                Model model) {
        try {

            String decoded = URLDecoder.decode(name, "UTF-8");
            ProductDTO product = productService.findByNamePaged(decoded, PageRequest.of(0, 1)).getContent().get(0);
            List<SizeDTO> availableSizes = productSizeService.getSizesByProductId(product.getId());

            // Set the price based on the selected size
            if (sizeId != null) {
                double productPriceBySize = productService.getProductPriceBySize(product.getId(), sizeId);
                product.setSelectedSizeId(sizeId);
                product.setPrice(productPriceBySize);
            }

            double discountedPrice = productService.getDiscountedPrice(product.getId());

            if (discountedPrice < product.getPrice()) {
                product.setIsDiscounted(true);
                product.setDiscountedPrice(discountedPrice);
            } else {
                product.setIsDiscounted(false);
            }

            // Get the discount percent
            DiscountDTO discount = discountService.getDiscountByProductId(product.getId());
            Double discountPercent = (discount != null) ? discount.getPercent() : 0.0;
            //Get reviews list
            List<FeedBackDTO> feedbackList;

            if (starRating == 0) {
                feedbackList = feedbackService.getFeedbacksForProduct(product.getId());
            } else {
                feedbackList = feedbackService.getFeedbacksForProductByRating(product.getId(), starRating);
            }

            for (FeedBackDTO feedback : feedbackList) {
                Account account = feedBackRepository.findAccountByFeedBackId(feedback.getId());
                feedback.setAccountDTO(accountMapper.accountToAccountDTO(account));
            }

            double averageRating = feedbackService.getAverageRating(product.getId());

            // Pagination
            int pageSize = 5;
            int totalPages = (int) Math.ceil((double) feedbackList.size() / pageSize);

            if (page < 0) {
                page = 0;
            } else if (page >= totalPages) {
                page = totalPages - 1;
            }

            int start = page * pageSize;
            int end = Math.min((start + pageSize), feedbackList.size());

            if (start >= 0 && end <= feedbackList.size()) {
                List<FeedBackDTO> pagedFeedbackList = feedbackList.subList(start, end);
                model.addAttribute("feedbackList", pagedFeedbackList);
            }
            // Show the comments
            model.addAttribute("discountPercent", discountPercent);
            model.addAttribute("product", product);
            model.addAttribute("availableSizes", availableSizes);

            model.addAttribute("name", name);
            model.addAttribute("averageRating", averageRating);

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);

            return "user/product/detail";
        } catch (UnsupportedEncodingException e) {
            return "error";
        }
    }

    @GetMapping("/getProductPriceBySize")
    public ResponseEntity<Double> getProductPriceBySize(
            @RequestParam("productId") Long productId,
            @RequestParam("sizeId") Long sizeId) {
        try {
            double productPrice = productService.getProductPriceBySize(productId, sizeId);
            return ResponseEntity.ok(productPrice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1.0);
        }
    }

    @GetMapping("/getDiscountPercentByProductId")
    public ResponseEntity<Double> getDiscountPercentByProductId(@RequestParam("productId") Long productId) {
        double discountPercent = productService.getDiscountPercentByProductId(productId);
        return ResponseEntity.ok(discountPercent);
    }

}