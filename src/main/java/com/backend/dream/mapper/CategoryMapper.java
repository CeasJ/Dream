package com.backend.dream.mapper;

import com.backend.dream.dto.CategoryDTO;
import com.backend.dream.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "discount.id", target = "id_discount")
    @Mapping(source = "discount.name", target = "name_discount")
    @Mapping(source = "discount.percent", target = "percent_discount")
    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);


}
