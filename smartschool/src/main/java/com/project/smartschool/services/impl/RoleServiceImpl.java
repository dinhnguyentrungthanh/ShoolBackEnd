package com.project.smartschool.services.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.smartschool.dto.request.RoleRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.RoleResponse;
import com.project.smartschool.entities.RoleEntity;
import com.project.smartschool.repository.RoleRepository;
import com.project.smartschool.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<RoleEntity> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public RoleEntity findById(String id) {
		return roleRepository.findById(id).orElse(null);
	}

	@Override
	public RoleEntity save(RoleEntity entity) {
		return roleRepository.save(entity);
	}

	@Override
	public RoleEntity update(RoleEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Boolean> deleteByIds(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleEntity> saveAll(List<RoleEntity> entities) {
		return roleRepository.saveAll(entities);
	}

	@Override
	public boolean isExistedById(String id) {
		return roleRepository.existsById(id);
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RoleEntity> findAllByIds(List<String> ids) {
		return StreamSupport.stream(roleRepository.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public List<RoleResponse> fetchAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleResponse fetchById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleResponse saveUsingDTO(RoleRequest inputDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleResponse updateUsingDTO(RoleRequest inputDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectResponsePaging<RoleResponse> findAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectResponsePaging<RoleResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
