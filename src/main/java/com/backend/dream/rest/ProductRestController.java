package com.backend.dream.rest;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.service.ProductService;
import com.backend.dream.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSizeService productSizeService;

    @GetMapping("/{id}")
    public ProductDTO getOne(@PathVariable("id") Long id) {
        return productService.findById(id);
    }
    @GetMapping("/{product_id}/{size_id}")
    public ProductSizeDTO getProductSizeDTOByID(@PathVariable("product_id") Long product,@PathVariable("size_id") Long size){
        return productSizeService.getProductSizeByProductIdAndSizeId(product,size);
    }

    @GetMapping()
    public List<ProductDTO> getAll() throws Exception {
        return productService.findAll();
    }

    @PostMapping()
    public Product create(@RequestBody ProductDTO productDTO) {
        return productService.create(productDTO);
    }

    @PutMapping("{id}")
    public ProductDTO update(@RequestBody ProductDTO productDTO, @PathVariable("id") Long id) {
        return productService.update(productDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @GetMapping("/getProductPriceByName")
    public ResponseEntity<Double> getProductPriceByName(
            @RequestParam("productName") String productName,
            @RequestParam("sizeId") Long sizeId) {
        try {
            ProductDTO product = productService.findByNamePaged(productName, PageRequest.of(0, 1)).getContent().get(0);
            double productPrice = productService.getProductPriceBySize(product.getId(), sizeId);
            return ResponseEntity.ok(productPrice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1.0);
        }
    }

}
