package com.backend.dream.service;

import com.backend.dream.dto.VoucherDTO;
import com.backend.dream.dto.VoucherStatusDTO;

import java.util.List;

public interface VoucherService {
    List<VoucherDTO> getApplicableVouchers(String username);

    List<VoucherDTO> getAllVouchers();

    // Display the voucher status in combobox
    List<VoucherStatusDTO> getAllVoucherStatus();

    List<VoucherDTO> getVouchersByStatusId(Long statusId);

    List<VoucherDTO> searchVouchersByName(String name);

    void delete(Long id);
}
