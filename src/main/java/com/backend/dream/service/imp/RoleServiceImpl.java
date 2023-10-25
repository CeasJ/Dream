package com.poly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.RoleDAO;
import com.poly.entity.Role;
import com.poly.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleDAO roleDAO;

	@Override
	public List<Role> findALL() {
		return roleDAO.findAll();
	}

	@Override
	public Role findById(String string) {
		return roleDAO.findById(string).get();
	}

	@Override
	public List<Role> findAllById(List<String> targetRoleIds) {
		return roleDAO.findAllById(targetRoleIds);
	}
}
