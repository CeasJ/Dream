package com.backend.dream.controller;
import com.backend.dream.dto.CategoryDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.mapper.ProductMapper;
import com.backend.dream.repository.ProductRepository;
import com.backend.dream.service.CategoryService;
import com.backend.dream.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productRepository.save(
                productMapper.productDTOToProduct(productDTO)), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products/all", method = RequestMethod.GET)
    public ResponseEntity<List<ProductDTO>> findAll() {
        return new ResponseEntity<>(productMapper.productsToProductDTOs(productRepository.findAll()), HttpStatus.OK);
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

    @GetMapping("/all")
    public String listProducts(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductDTO> productPage = productService.findAll(pageable);
        List<ProductDTO> productDTOs = productPage.getContent(); // Lấy danh sách sản phẩm từ trang hiện tại
        model.addAttribute("products", productDTOs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "products";
    }


}







