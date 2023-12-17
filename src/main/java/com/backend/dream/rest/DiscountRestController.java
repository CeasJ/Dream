package com.backend.dream.rest;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.mapper.DiscountMapper;
import com.backend.dream.service.DiscountService;
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
@RestController
@RequestMapping("/rest/discount")
public class DiscountRestController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private DiscountMapper discountMapper;
    @GetMapping()
    public List<DiscountDTO> getAll() throws Exception {
        return discountService.findAll();
    }
    @PostMapping()
    public DiscountDTO create(@RequestBody DiscountDTO discountDTO) {
        return discountService.createDiscount(discountDTO);
    }
    @PutMapping("{id}")
    public DiscountDTO update(@RequestBody DiscountDTO discountDTO, @PathVariable("id") Long id) {
        return discountService.update(discountDTO);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        discountService.delete(id);
    }

    @GetMapping("/download")
    private ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName ="Data-discount.xlsx";
        ByteArrayInputStream inputStream = discountService.getdataDiscount();
        InputStreamResource response = new InputStreamResource(inputStream);

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
        return responseEntity;
    }
}
