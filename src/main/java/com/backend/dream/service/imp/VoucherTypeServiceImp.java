package com.backend.dream.service.imp;

import com.backend.dream.mapper.VoucherTypeMapper;
import com.backend.dream.repository.VoucherTypeRepository;
import com.backend.dream.service.VoucherTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherTypeServiceImp implements VoucherTypeService {
    @Autowired
    private VoucherTypeRepository voucherTypeRepository;
    @Autowired
    private VoucherTypeMapper voucherTypeMapper;
}
