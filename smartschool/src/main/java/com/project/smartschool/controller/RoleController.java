package com.project.smartschool.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartschool.constraints.ApiConstraint;
import com.project.smartschool.constraints.PagingContraint;
import com.project.smartschool.dto.response.MajorResponse;
import com.project.smartschool.dto.response.MathDesignResponse;
import com.project.smartschool.dto.response.RoleResponse;
import com.project.smartschool.entities.RoleEntity;
import com.project.smartschool.entities.RoleGroupEntity;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.services.RoleGroupService;
import com.project.smartschool.services.RoleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleGroupService roleGroupService;
	
	@PostMapping(ApiConstraint.API_ENPOINT_ROLE)
	public Object insertRole() {
		List<RoleEntity> list = new ArrayList<RoleEntity>();
		for(ERole e : ERole.values()) {
			RoleEntity entity = new RoleEntity();
			entity.setCode(e.getCode());
			entity.setName(e);
			list.add(entity);
		}
		
		return roleService.saveAll(list);
	}
	
//	@PostMapping(ApiConstraint.API_ENPOINT_ROLE_GROUP)
//	public Object insertRoleGroup() {
//		Set<String> roles = new HashSet<String>();
//		RoleGroupEntity rgroup = new RoleGroupEntity();
//		rgroup.setName("Full Control");
//		
//		for(ERole e : ERole.values()) {
//			roles.add(e.name());
//		}
//		rgroup.setRoles(roles);
//		return roleGroupService.save(rgroup);
//	}
//	
//	@PutMapping(ApiConstraint.API_ENPOINT_ROLE_GROUP)
//	public Object updateRoleGroup() {
//		
//		Set<String> roleIds = roleService.findAll().stream().map(e -> e.getId()).distinct().collect(Collectors.toSet());		
//		RoleGroupEntity rgroup = roleGroupService.findById("605c4adebef9006327e560da");
//		rgroup.setRoles(roleIds);
//		return roleGroupService.save(rgroup);
//	}
	
	@ApiOperation(value = "Lấy tất cả các Role", response = RoleResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = RoleResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_ROLE)
	public Object getAllWithPage() {
		return roleService.findAll();
	}
	
	@ApiOperation(value = "Lấy tất cả các Quyền theo id", response = RoleResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = RoleResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_ROLE + "/{id}")
	public Object getById(@PathVariable String id) {
		return roleService.findById(id);
	}
}
