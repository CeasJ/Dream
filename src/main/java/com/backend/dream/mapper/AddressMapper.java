package com.backend.dream.mapper;
import com.backend.dream.dto.AddressDTO;
import com.backend.dream.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "id_account", source = "account.id")
    AddressDTO addressToAddressDTO(Address address);

    @Mapping(target = "account.id", source = "id_account")
    Address addressDTOToAddress(AddressDTO addressDTO);
}