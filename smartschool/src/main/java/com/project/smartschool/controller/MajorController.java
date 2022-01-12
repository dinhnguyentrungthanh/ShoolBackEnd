package com.project.smartschool.controller;

import java.util.List;
import java.util.Set;

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
import com.project.smartschool.dto.request.MajorRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.dto.response.MajorResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.services.MajorService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_MAJOR, tags = "Major Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class MajorController {

	@Autowired
	private MajorService majorService;
	
	@ApiOperation(value = "Thêm Môn", response = MajorResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MajorResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MAJOR_CREATE})
	@PostMapping(ApiConstraint.API_ENPOINT_MAJOR)
	public Object insert(@Valid @RequestBody MajorRequest majorRequest) throws ValidateException {
		return majorService.saveUsingDTO(majorRequest);
	}
	@ApiOperation(value = "cập nhật Môn", response = MajorResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MajorResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MAJOR_UPDATE})
	@PutMapping(ApiConstraint.API_ENPOINT_MAJOR + "/{id}")
	public Object update(@Valid @RequestBody MajorRequest majorRequest, @PathVariable String id) throws ValidateException {		
		return majorService.updateUsingDTO(majorRequest);
	}
	@ApiOperation(value = "Lấy tất cả các khối theo paging", response = MajorResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MajorResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_MAJOR)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
			return majorService.fetchAll();
		return majorService.findAll(currentPage, size);
	}
	@ApiOperation(value = "Lấy tất cả các khối theo id", response = MajorResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MajorResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_MAJOR + "/{id}")
	public Object getById(@PathVariable String id) {
		return majorService.findById(id);
	}
	

	@ApiOperation(value = "Xóa môn theo id", response = MajorResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MajorResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MAJOR_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_MAJOR + "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(majorService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách Môn")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MAJOR_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_MAJOR)
	public Object deleteMajors(@RequestBody List<String> ids) {
			return majorService.deleteByIds(ids);
	}
	
	@ApiOperation(value = "lấy danh sách Môn theo nhiều id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PatchMapping(value = ApiConstraint.API_ENPOINT_MAJOR + "/ids")
	public Object findbybeIds(@RequestBody Set<String> ids) {
			return majorService.findAllByIds(ids);
	}
}
