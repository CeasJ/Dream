package com.backend.dream.service.imp;

import com.backend.dream.dto.VoucherDTO;
import com.backend.dream.entity.Voucher;
import com.backend.dream.mapper.VoucherMapper;
import com.backend.dream.repository.VoucherRepository;
import com.backend.dream.service.AccountService;
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

    @Autowired
    AccountService accountService;

    @Override
    public List<VoucherDTO> getApplicableVouchers(String username) {
        Long userId = accountService.findIDByUsername(username);

        List<Voucher> applicableVouchers = voucherRepository.findApplicableVouchers(userId);
        return applicableVouchers.stream()
                .map(voucherMapper::voucherToVoucherDTO)
                .collect(Collectors.toList());
    }
}