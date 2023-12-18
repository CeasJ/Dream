package com.backend.dream.rest;

import com.backend.dream.dto.NotificationDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.service.AccountService;
import com.backend.dream.service.NotificationService;
import com.backend.dream.service.ProductService;
import com.backend.dream.service.ProductSizeService;
import com.backend.dream.util.ValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSizeService productSizeService;
    @Autowired
    private ValidationService validateService;
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
    public ResponseEntity<?> create(@RequestBody @Valid ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            validateService.validation(bindingResult);
            return ResponseEntity.badRequest().body(validateService.validation(bindingResult));
        }

        return  ResponseEntity.ok(productService.create(productDTO));
    }

    @PutMapping("{id}")
    public ProductDTO update(@RequestBody ProductDTO productDTO, @PathVariable("id") Long id, HttpServletRequest request) {
        String username = request.getRemoteUser();
        Long idAccount = accountService.findIDByUsername(username);
        Long idRole = accountService.findRoleIdByUsername(username);

        if (idRole == 1 || idRole == 2) {
            ProductDTO previousProduct = productService.findById(id);

            ProductDTO updatedProduct = productService.update(productDTO);

            String notificationTitle = "Có sự thay đổi trong sản phẩm";
            String notificationText = "Sản phẩm '" + previousProduct.getName() + "' đã được cập nhật bởi '" + username + "'";

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setIdAccount(idAccount);
            notificationDTO.setNotificationTitle(notificationTitle);
            notificationDTO.setNotificationText(notificationText);
            notificationDTO.setId_role(idRole);
            notificationDTO.setImage("product-change.jpg");
            notificationDTO.setCreatedTime(Timestamp.from(Instant.now()));

            notificationService.createNotification(notificationDTO);

            return updatedProduct;
        }

        return null;
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id, HttpServletRequest request) {
        String username = request.getRemoteUser();
        Long idAccount = accountService.findIDByUsername(username);
        Long idRole = accountService.findRoleIdByUsername(username);

        if (idRole == 1 || idRole == 2) {
            ProductDTO deletedProduct = productService.findById(id);

            String notificationTitle = "Có sự thay đổi trong sản phẩm";
            String notificationText = "Sản phẩm '" + deletedProduct.getName() + "' đã được xóa bởi '" + username + "'";

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setIdAccount(idAccount);
            notificationDTO.setNotificationTitle(notificationTitle);
            notificationDTO.setNotificationText(notificationText);
            notificationDTO.setId_role(idRole);
            notificationDTO.setImage("product-change.jpg");
            notificationDTO.setCreatedTime(Timestamp.from(Instant.now()));
            notificationService.createNotification(notificationDTO);
            productService.delete(id);
        }

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
