package com.backend.dream.mapper;

import com.backend.dream.dto.ProfileDTO;
import com.backend.dream.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(source = "profile.id",target = "id_account")
    ProfileDTO profileToProfileDTO(Profile profile);
//    @Mapping(source = "id_account",target = "account")
    Profile profileDTOToProfile(ProfileDTO profileDTO);
}
