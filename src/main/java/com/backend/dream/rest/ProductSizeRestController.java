package com.backend.dream.rest;

import com.backend.dream.dto.NotificationDTO;
import com.backend.dream.dto.ProductDTO;
import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.ProductSize;
import com.backend.dream.service.AccountService;
import com.backend.dream.service.NotificationService;
import com.backend.dream.service.ProductService;
import com.backend.dream.service.ProductSizeService;
import com.backend.dream.util.ValidationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("/rest/productsizes")
@RestController
public class ProductSizeRestController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductSizeService productSizeService;
    @Autowired
    private ValidationService validateService;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public List<ProductSizeDTO> getAllSizes() {
        return productSizeService.findAll();
    }

    @PostMapping()
    public ResponseEntity<?> productSize(@RequestBody @Valid ProductSizeDTO productSizeDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            validateService.validation(bindingResult);
            return ResponseEntity.badRequest().body(validateService.validation(bindingResult));
        }

        return ResponseEntity.ok(productSizeService.create(productSizeDTO));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id, HttpServletRequest request) {
        String username = request.getRemoteUser();
        Long idAccount = accountService.findIDByUsername(username);
        Long idRole = accountService.findRoleIdByUsername(username);

        if (idRole == 1 || idRole == 2) {
            String productName = productSizeService.getProductNameByProductSizeID(id);
            if (productName != null) {
                String notificationTitle = "Có sự thay đổi trong sản phẩm";
                String notificationText = "Một giá của sản phẩm '" + productName + "' đã được xóa bởi '" + username
                        + "'";

                NotificationDTO notificationDTO = new NotificationDTO();
                notificationDTO.setIdAccount(idAccount);
                notificationDTO.setNotificationTitle(notificationTitle);
                notificationDTO.setNotificationText(notificationText);
                notificationDTO.setId_role(idRole);
                notificationDTO.setImage("size-change.jpg");
                notificationDTO.setCreatedTime(Timestamp.from(Instant.now()));
                notificationService.createNotification(notificationDTO);
                productSizeService.delete(id);
            }
        }
    }
    @GetMapping("/download")
    private ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName ="Data-productSizes.xlsx";
        ByteArrayInputStream inputStream = productSizeService.getdataProductSize();
        InputStreamResource response = new InputStreamResource(inputStream);

    @GetMapping("/search")
    public List<ProductDTO> searchByProductIdAndSizeId(@RequestParam String name) {
        return productService.searchProductByName(name);
    }

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
        return responseEntity;
    }
}
