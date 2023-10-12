package com.dream.mapper;

import com.dream.dto.ProductDTO;
import com.dream.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.name", target = "category")
    ProductDTO productToProductDTO(Product product);
    @Mapping(source = "category", target = "category.name")
    Product productDTOToProduct(ProductDTO product);
}