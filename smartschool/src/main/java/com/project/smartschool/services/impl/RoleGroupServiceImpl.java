package com.project.smartschool.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.smartschool.dto.request.RoleGroupRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.RoleGroupResponse;
import com.project.smartschool.entities.RoleEntity;
import com.project.smartschool.entities.RoleGroupEntity;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.RoleGroupRepository;
import com.project.smartschool.services.RoleGroupService;
import com.project.smartschool.services.RoleService;

@Service
public class RoleGroupServiceImpl implements RoleGroupService {
	
	@Autowired
	private RoleGroupRepository roleGroupRepository;
	
	@Autowired 
	private RoleGroupService roleGroupService;
	
	@Autowired
	private RoleService roleService;
	

	@Override
	public List<RoleGroupEntity> findAll() {
		return roleGroupRepository.findAll();
	}

	@Override
	public RoleGroupEntity findById(String id) {
		return roleGroupRepository.findById(id).orElse(null);
	}

	@Override
	public RoleGroupEntity save(RoleGroupEntity entity) {
		return roleGroupRepository.save(entity);
	}

	@Override
	public RoleGroupEntity update(RoleGroupEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		RoleGroupEntity roleGroupEntity = verify(id);
		roleGroupEntity.setEnable(false);
		return roleGroupRepository.save(roleGroupEntity) != null;
	}

	@Override
	public Map<String, Boolean> deleteByIds(List<String> ids) {
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for(String id: ids) {
			try {
				this.deleteById(id);
				m.put(id, true);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				m.put(id, false);
			}
		}
		return m;
	}

	@Override
	public List<RoleGroupEntity> saveAll(List<RoleGroupEntity> entities) {
		return roleGroupRepository.saveAll(entities);
	}

	@Override
	public boolean isExistedById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RoleGroupEntity> findAllByIds(List<String> ids) {
		return StreamSupport.stream(roleGroupRepository.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public List<RoleGroupResponse> fetchAll() {
		List<RoleGroupEntity>  roleGroupEntities = roleGroupRepository.findAllByEnableTrue();
		return roleGroupEntities.stream().map(c -> mappingRoleGroupToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public RoleGroupResponse fetchById(String id) {
		RoleGroupEntity roleGroupEntity = roleGroupRepository.findById(id).orElse(null);
		
		if(ObjectUtils.anyNull(roleGroupEntity)) 
			throw new ValidateException("Không tìm thấy Nhóm Quyền");
		
		return mappingRoleGroupToResponse(roleGroupEntity);
	}

	@Override
	public RoleGroupResponse saveUsingDTO(RoleGroupRequest inputDto) {
		if(roleGroupService.isExistedByName(inputDto.getName()))
			throw new ValidateException("Tên Quyền này đã tồn tại");
		
		return saveOrUpdateUsingDTO(inputDto, inputDto.convertToEntity());
	}

	@Override
	public RoleGroupResponse updateUsingDTO(RoleGroupRequest inputDto) {
		RoleGroupEntity rg = verify(inputDto.getId());
		
		RoleGroupEntity rgAll = roleGroupRepository.findByName(inputDto.getName());
		
		if(ObjectUtils.allNotNull(rg) && ObjectUtils.allNotNull(rgAll)) {
			if(!inputDto.getId().equals(rgAll.getId()) && inputDto.getName().trim().equals(rgAll.getName())) {
				throw new ValidateException("Tên Quyền này đã tồn tại");
			}
			
			if(inputDto.getId().equals(rgAll.getId()) && !inputDto.getName().trim().equals(rgAll.getName())) {
				throw new ValidateException("Tên Quyền này đã tồn tại");
			}
		} 			
		
//		boolean hasAnyMajorNotExisted = inputDto.getRoles().stream().anyMatch(e -> !roleService.isExistedById(e));		
//		if(hasAnyMajorNotExisted) 
//			throw new ValidateException("Danh sách Quyền không hợp lệ");
		 return saveOrUpdateUsingDTO(inputDto, roleGroupService.findById(inputDto.getId()));

	}

	@Override
	public ObjectResponsePaging<RoleGroupResponse> findAll(int page, int size) {
		Page<RoleGroupEntity> pages = this.roleGroupRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<RoleGroupResponse> elements = pages.getContent().stream().map(c -> mappingRoleGroupToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<RoleGroupResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}
	 
	private RoleGroupResponse mappingRoleGroupToResponse(RoleGroupEntity rg) {	
		ObjectMapper o = new ObjectMapper();
		
		List<String> roleIds = rg.getRoles().stream().collect(Collectors.toList());
		List<RoleEntity> roles = roleService.findAllByIds(roleIds);
		try {
			System.err.println(o.writeValueAsString(roleIds));
			System.err.println(o.writeValueAsString(roles));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Binding data và convert to UserResponse
		return RoleGroupResponse.builder().build().convertFromEntity(rg, roles);
	}

	@Override
	public ObjectResponsePaging<RoleGroupResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExistedByName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RoleGroupEntity> findAllByIds(Set<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}
	private RoleGroupEntity verify(String id) {
		RoleGroupEntity mtd = roleGroupRepository.findById(id).orElse(null);
		if (ObjectUtils.anyNull(mtd)) {
			throw new ValidateException("Nhóm Quyền không tồn tại");
		}
		return mtd;
	}

	private RoleGroupResponse saveOrUpdateUsingDTO(RoleGroupRequest inputDto, RoleGroupEntity roleGroupEntity) {		
		RoleGroupEntity rg = roleGroupRepository.save(inputDto.convertToEntity(roleGroupEntity));		
		return RoleGroupResponse.builder().build().convertFromEntity(rg);
	}
}
