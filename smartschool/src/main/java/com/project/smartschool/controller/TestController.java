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
import com.project.smartschool.dto.request.TestRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.dto.response.TestResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.services.TestService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE + ApiConstraint.API_ENPOINT_TEST, tags = "Test Type Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class TestController {

	@Autowired
	private TestService testService;
	
	@ApiOperation(value = "Thêm câu kiểm tra", response = TestResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = TestResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_TEST_CREATE})
	@PostMapping(ApiConstraint.API_ENPOINT_TEST)
	public Object insert(@Valid @RequestBody TestRequest request) throws ValidateException {
		return testService.saveUsingDTO(request);
	}
	
	@ApiOperation(value = "Cập nhật câu kiểm tra", response = TestResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = TestResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_TEST_UPDATE})
	@PutMapping(ApiConstraint.API_ENPOINT_TEST_ID)
	public Object update(@Valid @RequestBody TestRequest request, @PathVariable String id) throws ValidateException {
		request.setId(id);
		
		return testService.updateUsingDTO(request);
	}
	
	@ApiOperation(value = "Lấy tất cả câu kiểm tra theo id", response = TestResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = TestResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_TEST_ID)
	public Object getById(@PathVariable String id) {
		return testService.fetchById(id);
	}
	
	@ApiOperation(value = "Lấy tất cả các Câu Hỏi kiểm tra theo paging", response = TestResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = TestResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_TEST)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size,
			@RequestParam(defaultValue = "") String testTypeId) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
			return testService.fetchAllByTestTypeId(testTypeId);
		return testService.findAllByTestTypeId(currentPage, size, testTypeId);
	}
	
	@ApiOperation(value = "Xóa Câu Hỏi kiểm tra theo id", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_TEST_TYPE_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_TEST_ID)
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(testService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách Câu Hỏi kiểm tra")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_TEST_TYPE_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_TEST)
	public Object deleteBlocks(@RequestBody List<String> ids) {
			return testService.deleteByIds(ids);
	}
	
	@ApiOperation(value = "lấy danh sách Môn theo nhiều id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PatchMapping(value = ApiConstraint.API_ENPOINT_TEST + "/ids")
	public Object findbybeIds(@RequestBody List<String> ids) {
			return testService.findAllByIds(ids);
	}
}
