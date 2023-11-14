package com.backend.dream.service;

import com.backend.dream.dto.VoucherDTO;

import java.util.List;

public interface VoucherService {
    List<VoucherDTO> getApplicableVouchers();
}
