package com.backend.dream.service;



import com.backend.dream.entity.Role;

import java.util.List;

public interface RoleService {
	List<Role> findALL();

	Role findById(Long id);

	List<Role> findAllById(List<Long> targetRoleIds);
}
