package com.dream.mapper;

import com.dream.dto.DiscountDTO;
import com.dream.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    DiscountMapper INSTANCE = Mappers.getMapper(DiscountMapper.class);
    @Mapping(source = "product.id", target = "id_product")
    DiscountDTO discountToDiscountDTO(Discount discount);

    Discount discountDTOToDiscount(DiscountDTO discountDTO);
}
