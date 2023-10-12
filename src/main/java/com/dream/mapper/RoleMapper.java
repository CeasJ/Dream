package com.dream.mapper;

import com.dream.dto.RoleDTO;
import com.dream.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    public RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
   public RoleDTO roleToRoleDTO(Role role);
   public Role roleDTOToRole(RoleDTO roleDTO);
}
