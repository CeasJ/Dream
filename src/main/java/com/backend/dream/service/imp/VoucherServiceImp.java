package com.backend.dream.service.imp;

import com.backend.dream.dto.VoucherDTO;
import com.backend.dream.entity.Voucher;
import com.backend.dream.mapper.VoucherMapper;
import com.backend.dream.repository.VoucherRepository;
import com.backend.dream.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImp implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherMapper voucherMapper;

    @Override
    public List<VoucherDTO> getApplicableVouchers() {
        List<Voucher> applicableVouchers = voucherRepository.findApplicableVouchers();
        return applicableVouchers.stream()
                .map(voucherMapper::voucherToVoucherDTO)
                .collect(Collectors.toList());
    }
}