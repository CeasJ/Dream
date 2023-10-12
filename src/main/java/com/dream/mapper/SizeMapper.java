package com.dream.mapper;

import com.dream.dto.SizeDTO;
import com.dream.entity.Size;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    SizeMapper INSTANCE = Mappers.getMapper(SizeMapper.class);

    SizeDTO sizeToSizeDTO(Size size);
    Size sizeDTOToSize(SizeDTO sizeDTO);
}
