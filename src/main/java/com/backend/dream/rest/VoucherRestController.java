package com.backend.dream.rest;

import com.backend.dream.dto.VoucherDTO;
import com.backend.dream.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherRestController {
    private final VoucherService voucherService;

    @Autowired
    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/applicable")
    public List<VoucherDTO> getApplicableVouchers(){
        return voucherService.getApplicableVouchers();
    }
}
