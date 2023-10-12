package com.dream.mapper;

import com.dream.dto.VoucherStatusDTO;
import com.dream.entity.VoucherStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoucherStatusMapper {
    VoucherStatusMapper INSTANCE = Mappers.getMapper(VoucherStatusMapper.class);
    VoucherStatusDTO voucherStatusToVoucherStatusDTO(VoucherStatus voucherStatus);
    VoucherStatus voucherStatusDTOToVoucherStatus(VoucherStatusDTO voucherStatusDTO);
}
