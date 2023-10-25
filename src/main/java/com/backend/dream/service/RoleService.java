package com.poly.service;

import java.util.List;

import com.poly.entity.Role;

public interface RoleService {
	List<Role> findALL();

	Role findById(String string);

	List<Role> findAllById(List<String> targetRoleIds);
}
