package com.project.smartschool.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.project.smartschool.dto.request.TestTypeRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.dto.response.TestTypeResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.services.TestTypeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE + ApiConstraint.API_ENPOINT_TEST_TYPE, tags = "Test Type Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class TestTypeController {

	@Autowired
	private TestTypeService testTypeService;
	
	@ApiOperation(value = "Thêm loại bài kiểm tra", response = TestTypeResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = TestTypeResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(ApiConstraint.API_ENPOINT_TEST_TYPE)
	public Object insert(@Valid @RequestBody TestTypeRequest request) throws ValidateException {
		return testTypeService.saveUsingDTO(request);
	}
	
	@ApiOperation(value = "Cập nhật loại bài kiểm tra", response = TestTypeResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = TestTypeResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PutMapping(ApiConstraint.API_ENPOINT_TEST_TYPE_ID)
	public Object insert(@Valid @RequestBody TestTypeRequest request, @PathVariable String id) throws ValidateException {		
		return testTypeService.updateUsingDTO(request);
	}
	
	@ApiOperation(value = "Lấy tất cả loại bài kiểm tra theo id", response = TestTypeResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = TestTypeResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_TEST_TYPE_ID)
	public Object getById(@PathVariable String id) {
		return testTypeService.fetchById(id);
	}
	
	@ApiOperation(value = "Lấy tất cả các loại bài kiểm tra theo paging", response = TestTypeResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = TestTypeResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_TEST_TYPE)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size,
			@RequestParam(defaultValue = "") String blockSeo) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING)) {
			if(StringUtils.isNotBlank(blockSeo)) {
				return testTypeService.fetchAllByBlockSeo(blockSeo);
			}
			return testTypeService.fetchAll();
		}
		if(StringUtils.isNotBlank(blockSeo)) {
			return testTypeService.findAllByBlockSeo(currentPage, size, blockSeo);
		}
		return testTypeService.findAll(currentPage, size);
	}
	
	@ApiOperation(value = "Xóa bài kiểm tra theo id", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_TEST_TYPE_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_TEST_TYPE_ID)
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(testTypeService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách Bài kiểm tra")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_TEST_TYPE_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_TEST_TYPE)
	public Object deleteBlocks(@RequestBody List<String> ids) {
			return testTypeService.deleteByIds(ids);
	}
	
}
