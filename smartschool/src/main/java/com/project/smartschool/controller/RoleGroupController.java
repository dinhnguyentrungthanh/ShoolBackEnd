package com.project.smartschool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartschool.constraints.ApiConstraint;
import com.project.smartschool.constraints.PagingContraint;
import com.project.smartschool.dto.request.MajorRequest;
import com.project.smartschool.dto.request.RoleGroupRequest;
import com.project.smartschool.dto.response.MajorResponse;
import com.project.smartschool.dto.response.MathDesignResponse;
import com.project.smartschool.dto.response.RoleGroupResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.services.RoleGroupService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_ROLE_GROUP, tags = "Role Group Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class RoleGroupController {
	
	@Autowired
	private RoleGroupService roleGroupService;
	
	@ApiOperation(value = "Lấy tất cả các khối theo paging", response = RoleGroupResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = RoleGroupResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_ROLEGROUP_VIEW})
	@GetMapping(ApiConstraint.API_ENPOINT_ROLE_GROUP)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size) {
			if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
				return roleGroupService.fetchAll();
		return roleGroupService.findAll(currentPage, size);
	}

	@ApiOperation(value = "Lấy tất cả các Quyền theo id", response = RoleGroupResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = RoleGroupResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_ROLEGROUP_VIEW})
	@GetMapping(ApiConstraint.API_ENPOINT_ROLE_GROUP + "/{id}")
	public Object getById(@PathVariable String id) {
		return roleGroupService.findById(id);
	}
	

	@ApiOperation(value = "Xóa Quyền theo id", response = RoleGroupResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = RoleGroupResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_ROLEGROUP_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_ROLE_GROUP + "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(roleGroupService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Thêm Quyền", response = RoleGroupResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MajorResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_ROLEGROUP_CREATE})
	@PostMapping(ApiConstraint.API_ENPOINT_ROLE_GROUP)
	public Object insert(@Valid @RequestBody RoleGroupRequest groupRequest) throws ValidateException {
		return roleGroupService.saveUsingDTO(groupRequest);
	}
	@ApiOperation(value = "cập nhật Quyền", response = RoleGroupResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MajorResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_ROLEGROUP_UPDATE})
	@PutMapping(ApiConstraint.API_ENPOINT_ROLE_GROUP + "/{id}")
	public Object update(@Valid @RequestBody RoleGroupRequest groupRequest, @PathVariable String id) throws ValidateException {		
		return roleGroupService.updateUsingDTO(groupRequest);
	}
}
