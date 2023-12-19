package com.backend.dream.rest;


import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.entity.ProductSize;
import com.backend.dream.repository.ProductSizeRepository;
import com.backend.dream.service.ProductSizeService;
import com.backend.dream.util.ExcelUltils;
import com.backend.dream.util.PdfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private ProductSizeRepository productSizeRepository;
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
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportToPdf() {
        try {
            List<ProductSize> productSizes = productSizeRepository.findAll();
            String title = "Data Product Size";
            ByteArrayInputStream pdfStream = PdfUtils.dataToPdf(productSizes, ExcelUltils.HEADERPRODUCTSIZE,title);

            byte[] pdfContents = new byte[pdfStream.available()];
            pdfStream.read(pdfContents);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Data-productSize.pdf");
            headers.setCacheControl("must-revalidate, no-store");

            return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
