package com.backend.dream.mapper;

import com.backend.dream.dto.VoucherDTO;
import com.backend.dream.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VoucherMapper {
    VoucherMapper INSTANCE = Mappers.getMapper(VoucherMapper.class);

    @Mapping(source = "account.id",target = "id_account")
    @Mapping(source = "status.name", target = "status")
    VoucherDTO voucherToVoucherDTO(Voucher voucher);

    @Mapping(source = "id_account",target = "account.id")
    // something wrong here check again
    @Mapping(source = "status",target = "status.name")
    Voucher voucherDTOToVoucher(VoucherDTO voucherDTO);


}
