package com.backend.dream.rest;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.Product;
import com.backend.dream.repository.ProductRepository;
import com.backend.dream.service.ProductService;
import com.backend.dream.service.ProductSizeService;
import com.backend.dream.util.ExcelUltils;
import com.backend.dream.util.PdfUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/products")
public class ProductRestController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
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
    @GetMapping("/download")
    private ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName ="Data-products.xlsx";
        ByteArrayInputStream inputStream = productService.getdataProduct();
        InputStreamResource response = new InputStreamResource(inputStream);

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
        return responseEntity;
    }
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportToPdf() {
        try {
            List<Product> products = productRepository.findAll();
            String title = "Data Product";
            ByteArrayInputStream pdfStream = PdfUtils.dataToPdf(products, ExcelUltils.HEADER_PRODUCT,title);

            // Chuyển đổi ByteArrayInputStream sang byte array
            byte[] pdfContents = new byte[pdfStream.available()];
            pdfStream.read(pdfContents);

            // Đặt headers để trình duyệt hiểu được định dạng của file PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Data-products.pdf");
            headers.setCacheControl("must-revalidate, no-store");

            return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
