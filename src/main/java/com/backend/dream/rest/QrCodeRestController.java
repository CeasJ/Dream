package com.backend.dream.rest;

import com.backend.dream.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Paths;

@CrossOrigin("**")
@RestController
@RequestMapping("/qrcode")
public class QrCodeRestController {
    @Autowired
    private QrCodeService qrCodeService;

    private static final String path = "D:\\Dream\\dream\\src\\main\\resources\\static\\img\\qrcode";

    @GetMapping()
    public ResponseEntity<Resource> getLatestQrCode() throws IOException {
        String fileName = qrCodeService.getQrCode();
        Resource resource = new UrlResource(Paths.get(path, fileName).toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
