package com.backend.dream.controller;
import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.dto.SizeDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.mapper.ProductMapper;
import com.backend.dream.repository.ProductRepository;
import com.backend.dream.service.CategoryService;
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
    private ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;





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
        return new ResponseEntity<>(productMapper.productToProductDTO(productRepository.findById(id).get()), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProductDTO> deleteById(@PathVariable(value = "id") Long id) {
        ProductDTO productDTO = productMapper.productToProductDTO(productRepository.findById(id).get());
        productRepository.deleteById(productDTO.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/store")
    public String showListProducts(
            @RequestParam(required = false) String sortOption,
            @RequestParam(name = "categoryId", required = false) String categoryIdString,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
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
        } else {
            // Mặc định
            productPage = productService.findByCategory(categoryIdValue, pageable);
        }

//        List<DiscountDTO> discounts = yourDiscountServiceMethod(); // Thay thế bằng phương thức lấy danh sách giảm giá
//        for (ProductDTO productDTO : productPage.getContent()) {
//            // Tìm giảm giá cho sản phẩm
//            for (DiscountDTO discount : discounts) {
//                if (discount.getId_product().equals(productDTO.getId())) {
//                    double discountedPrice = productDTO.getOriginalPrice() * (1 - (discount.getPercent() / 100));
//                    productDTO.setDiscountedPrice(discountedPrice);
//                    break;
//                }
//            }
//        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("categoryId", categoryIdValue);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "user/product/products-list";
    }



    @GetMapping("/search")
    public String searchByName(
            @RequestParam String productName,
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
    public String productDetail(@PathVariable(value = "name") String name, Model model) {
        try {
            String decoded = URLDecoder.decode(name, "UTF-8");
            ProductDTO product = productService.findByNamePaged(decoded, PageRequest.of(0, 1)).getContent().get(0);
            List<SizeDTO> availableSizes = productSizeService.getSizesByProductId(product.getId());
            model.addAttribute("product", product);
            model.addAttribute("availableSizes", availableSizes);

            System.out.println(availableSizes);
            return "user/product/detail";
        } catch (UnsupportedEncodingException e) {
            return "error";
        }
    }


}