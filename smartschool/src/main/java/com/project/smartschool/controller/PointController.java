package com.project.smartschool.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.project.smartschool.dto.request.PointRequest;
import com.project.smartschool.dto.response.PointResponse;
import com.project.smartschool.entities.PointEntity;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.enums.ETestType;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.helpers.classes.TestResult;
import com.project.smartschool.services.PointService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE + ApiConstraint.API_ENPOINT_POINT, tags = "Point Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class PointController {

	@Autowired
	private PointService pointService;
	
	@ApiOperation(value = "Thêm loại bài kiểm tra", response = PointResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = PointResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	//@Secured({ERole.Names.ROLE_POINT_CREATE})
	@PostMapping(ApiConstraint.API_ENPOINT_POINT)
	public Object insert(@Valid @RequestBody PointRequest request) throws ValidateException {
		return pointService.saveUsingDTO(request);
	}
	
	@ApiOperation(value = "Cập nhật loại bài kiểm tra", response = PointResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = PointResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	//@Secured({ERole.Names.ROLE_POINT_UPDATE})
	@PutMapping(ApiConstraint.API_ENPOINT_POINT_ID)
	public Object insert(@Valid @RequestBody PointRequest request, @PathVariable String id) throws ValidateException {
		request.setId(id);		
		return pointService.updateUsingDTO(request);
	}
	
	@ApiOperation(value = "Cập nhật đáp án bài KT", response = PointResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = PointResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	//@Secured({ERole.Names.ROLE_POINT_UPDATE})
	@PutMapping(ApiConstraint.API_ENPOINT_POINT_ID_STREAM)
	public Object updateStream(@Valid @RequestBody PointRequest request, @PathVariable String id) throws ValidateException {	
		 pointService.updateTestResultStream(id, request);		 
		 return null;
	}
	
	@ApiOperation(value = "Lấy tất cả loại bài kiểm tra theo id", response = PointResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = PointResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	//@Secured({ERole.Names.ROLE_POINT_VIEW})
	@GetMapping(ApiConstraint.API_ENPOINT_POINT_ID)
	public Object getById(@PathVariable String id) {
		return pointService.fetchById(id);
	}
	
	@ApiOperation(value = "Lấy tất cả các loại bài kiểm tra theo paging", response = PointResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = PointResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	//@Secured({ERole.Names.ROLE_POINT_VIEW})
	@GetMapping(ApiConstraint.API_ENPOINT_POINT)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size,
			@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String type) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING)) {
			if(StringUtils.isNotBlank(userId)) {
				return pointService.fetchAllByUser(userId);
			}
			
			if(StringUtils.isNotBlank(type)) {
				return pointService.fetchAllByType(type);
			}
			return pointService.fetchAll();
		}
		
		if(StringUtils.isNotBlank(userId)) {
			return pointService.findAllByUser(currentPage, size, userId);
		}
		
		if(StringUtils.isNotBlank(type)) {
			return pointService.findAllByType(currentPage, size, type);
		}
		
		return pointService.findAll(currentPage, size);
	}
	
}
