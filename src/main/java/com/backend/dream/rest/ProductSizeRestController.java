package com.backend.dream.rest;


import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.ProductSize;
import com.backend.dream.service.ProductSizeService;
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
    private ProductSizeService productSizeService;
    @GetMapping()
    public List<ProductSizeDTO> getAllSizes() {
        return productSizeService.findAll();
    }
    @PostMapping()
    public ProductSize productSize(@RequestBody ProductSizeDTO productSizeDTO) {
        return productSizeService.create(productSizeDTO);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        productSizeService.delete(id);
    }
    @PutMapping("{id}")
    public ProductSizeDTO productSizeDTO(@RequestBody ProductSizeDTO productSizeDTO,@PathVariable("id") Long id){
        return productSizeService.update(productSizeDTO,id);
    }
    @GetMapping("/download")
    private ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName ="Data-productSizes.xlsx";
        ByteArrayInputStream inputStream = productSizeService.getdataProductSize();
        InputStreamResource response = new InputStreamResource(inputStream);

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
        return responseEntity;
    }
}
