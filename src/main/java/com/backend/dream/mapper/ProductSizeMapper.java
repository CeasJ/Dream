package com.backend.dream.mapper;

import com.backend.dream.dto.ProductSizeDTO;
import com.backend.dream.entity.ProductSize;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductSizeMapper {
    ProductSizeMapper INSTANCE = Mappers.getMapper(ProductSizeMapper.class);

    @Mapping(source = "size.id", target = "id_size")
    ProductSizeDTO productSizeToProductSizeDTO(ProductSize productSize);

    @Mapping(source = "id_size", target = "id_product")
    ProductSizeDTO productSizeDTOToProductSize(ProductSizeDTO productSizeDTO);
}
