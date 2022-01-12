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
import com.project.smartschool.dto.request.MathDesignRequest;
import com.project.smartschool.dto.response.MathDesignResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.services.MathDesignService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_MATHDESIGN, tags = "MathDesign Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class MathDesignController {

	@Autowired
	private MathDesignService mathDesignService;
	
	@ApiOperation(value = "Thêm Dạng Toán", response = MathDesignResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MathDesignResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MATHDESIGN_CREATE})
	@PostMapping(ApiConstraint.API_ENPOINT_MATHDESIGN)
	public Object insert(@Valid @RequestBody MathDesignRequest mathDesignRequest) throws ValidateException {
		return mathDesignService.saveUsingDTO(mathDesignRequest);
	}
	@ApiOperation(value = "cập nhật khối", response = MathDesignResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MathDesignResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MATHDESIGN_UPDATE})
	@PutMapping(ApiConstraint.API_ENPOINT_MATHDESIGN + "/{id}")
	public Object update(@Valid @RequestBody MathDesignRequest mathDesignRequest, @PathVariable String id) throws ValidateException {		
		return mathDesignService.updateUsingDTO(mathDesignRequest);
	}
	@ApiOperation(value = "Lấy tất cả các khối theo paging", response = MathDesignResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MathDesignResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_MATHDESIGN)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size) {
			if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
				return mathDesignService.fetchAll();
		return mathDesignService.findAll(currentPage, size);
	}
	
	@ApiOperation(value = "Lấy tất cả các khối theo id", response = MathDesignResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MathDesignResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_MATHDESIGN + "/{id}")
	public Object getById(@PathVariable String id) {
		return mathDesignService.findById(id);
	}	
	
	@ApiOperation(value = "Xóa môn theo id", response = MathDesignResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MathDesignResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MATHDESIGN_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_MATHDESIGN + "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(mathDesignService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách Môn")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MATHDESIGN_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_MATHDESIGN)
	public Object deleteMathDesign(@RequestBody List<String> ids) {
			return mathDesignService.deleteByIds(ids);
	}
	
	@ApiOperation(value = "lấy danh sách Dạng Toán theo nhiều id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PatchMapping(value = ApiConstraint.API_ENPOINT_MATHDESIGN + "/ids")
	public Object findbybeIds(@RequestBody Set<String> ids) {
			return mathDesignService.findAllByIds(ids);
	}

}
