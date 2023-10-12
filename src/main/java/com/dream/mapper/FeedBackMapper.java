package com.dream.mapper;

import com.dream.dto.FeedBackDTO;
import com.dream.entity.FeedBack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FeedBackMapper {
    FeedBackMapper INSTANCE = Mappers.getMapper(FeedBackMapper.class);

    @Mapping(source = "account.id",target = "id_account")
    @Mapping(source = "product.id", target = "id_product")
    FeedBackDTO feedBackToFeedBackDTO(FeedBack feedback);

//    @Mapping(source = "id_account",target = "account.id")
//    @Mapping(source = "id_product", target = "product.id")
    FeedBack feedBackDTOToFeedBack(FeedBackDTO feedbackDTO);
}
