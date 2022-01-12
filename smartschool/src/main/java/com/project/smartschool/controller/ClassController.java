package com.project.smartschool.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartschool.constraints.ApiConstraint;
import com.project.smartschool.constraints.PagingContraint;
import com.project.smartschool.dto.request.ClassRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.dto.response.ClassResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.services.ClassService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_CLASS, tags = "Class Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class ClassController {

	@Autowired
	private ClassService classService;

	@ApiOperation(value = "Thêm lớp", response = ClassResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = ClassResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_CLASS_CREATE})
	@PostMapping(ApiConstraint.API_ENPOINT_CLASS)
	public Object insert(@Valid @RequestBody ClassRequest classRequest) throws ValidateException {
		return classService.saveUsingDTO(classRequest);
	}
	
	@ApiOperation(value = "Cập nhật  lớp", response = ClassResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = ClassResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_CLASS_UPDATE})
	@PutMapping(ApiConstraint.API_ENPOINT_CLASS_ID)
	public Object update(@Valid @RequestBody ClassRequest classRequest, @PathVariable String id) throws ValidateException {		
		return classService.updateUsingDTO(classRequest);
	}
	@ApiOperation(value = "Lấy tất cả các lớp theo paging", response = ClassResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = ClassResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	//@Secured(ERole.Names.ROLE_CLASS_VIEW)
	@GetMapping(ApiConstraint.API_ENPOINT_CLASS)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
			return classService.fetchAll();
		return classService.findAll(currentPage, size);
	}
	
	@ApiOperation(value = "Lấy tất cả các Lop theo id", response = ClassResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = ClassResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_CLASS + "/{id}")
	public Object getById(@PathVariable String id) {
		return classService.findById(id);
	}
	
	@ApiOperation(value = "Xóa lớp theo id", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_CLASS_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_CLASS + "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(classService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách Class")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_CLASS_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_CLASS)
	public Object deleteClass(@RequestBody List<String> ids) {
			return classService.deleteByIds(ids);
	}
	
	@ApiOperation(value = "Xóa User ra khỏi Class")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@DeleteMapping(value = ApiConstraint.API_ENPOINT_CLASS_ID_USER_ID)
	public Object deleteUserFromCLass(@PathVariable String classId, @PathVariable String userId) {
		return classService.deleteUsersFromClassByIds(classId, Arrays.asList(new String[]{userId}));
	}
	
	@ApiOperation(value = "Xóa danh sách User ra khỏi Class")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PatchMapping(value = ApiConstraint.API_ENPOINT_CLASS_ID_USER)
	public Object deleteUserFromCLass(@PathVariable String classId, @RequestBody List<String> userIds) {
		return classService.deleteUsersFromClassByIds(classId, userIds);
	}
}
