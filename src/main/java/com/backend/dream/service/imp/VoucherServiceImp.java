package com.backend.dream.service.imp;

import com.backend.dream.dto.VoucherDTO;
import com.backend.dream.dto.VoucherStatusDTO;
import com.backend.dream.entity.Voucher;
import com.backend.dream.entity.VoucherStatus;
import com.backend.dream.mapper.VoucherMapper;
import com.backend.dream.mapper.VoucherStatusMapper;
import com.backend.dream.repository.VoucherRepository;
import com.backend.dream.repository.VoucherStatusRepository;
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

    @Autowired
    VoucherStatusRepository voucherStatusRepository;

    @Override
    public List<VoucherDTO> getApplicableVouchers(String username) {
        Long userId = accountService.findIDByUsername(username);

        List<Voucher> applicableVouchers = voucherRepository.findApplicableVouchers(userId);
        return applicableVouchers.stream()
                .map(voucherMapper::voucherToVoucherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VoucherDTO> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherMapper.INSTANCE::voucherToVoucherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VoucherStatusDTO> getAllVoucherStatus() {
        List<VoucherStatus> voucherStatusList = voucherStatusRepository.findAll();
        return voucherStatusList.stream()
                .map(VoucherStatusMapper.INSTANCE::voucherStatusToVoucherStatusDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VoucherDTO> getVouchersByStatusId(Long statusId) {
        List<Voucher> vouchers = voucherRepository.findByStatusId(statusId);
        return vouchers.stream()
                .map(voucherMapper::voucherToVoucherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VoucherDTO> searchVouchersByName(String name) {
        List<Voucher> vouchers = voucherRepository.searchByName(name);
        return vouchers.stream()
                .map(voucherMapper::voucherToVoucherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        voucherRepository.deleteById(id);
    }
}