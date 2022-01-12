package com.project.smartschool.controller;

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
import com.project.smartschool.dto.request.BlockRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.dto.response.ClassResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.services.BlockService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_BLOCK, tags = "Block Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class BlockController {

	@Autowired
	private BlockService blockService;
	
	@ApiOperation(value = "Thêm khối", response = BlockResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_BLOCK_CREATE})
	@PostMapping(ApiConstraint.API_ENPOINT_BLOCK)
	public Object insert(@Valid @RequestBody BlockRequest blockRequest) throws ValidateException {
		return blockService.saveUsingDTO(blockRequest);
	}
	
	@ApiOperation(value = "Cập nhật khối", response = ClassResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_BLOCK_UPDATE})
	@PutMapping(ApiConstraint.API_ENPOINT_BLOCK + "/{id}")
	public Object update(@Valid @RequestBody BlockRequest blockRequest, @PathVariable String id) throws ValidateException {		
		return blockService.updateUsingDTO(blockRequest);
	}
	
	@ApiOperation(value = "Lấy tất cả các khối theo paging", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	//@Secured({ERole.Names.ROLE_BLOCK_VIEW})
	@GetMapping(ApiConstraint.API_ENPOINT_BLOCK)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
			return blockService.fetchAll();
		return blockService.findAll(currentPage, size);
	}
	
	@ApiOperation(value = "Xóa khối theo id", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_BLOCK_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_BLOCK_ID)
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(blockService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Lấy tất cả các khối theo id", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	//@Secured({ERole.Names.ROLE_BLOCK_VIEW})
	@GetMapping(ApiConstraint.API_ENPOINT_BLOCK_ID)
	public Object getById(@PathVariable String id) {
		return blockService.findById(id);
	}
	
	@ApiOperation(value = "Lấy tất cả các khối theo id", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	//@Secured({ERole.Names.ROLE_BLOCK_VIEW})
	@GetMapping(ApiConstraint.API_ENPOINT_BLOCK_BY_URL)
	public Object getByUrl(@PathVariable String url) {
		return blockService.findByUrl(url);
	}
	
	@ApiOperation(value = "Xóa danh sách Block")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_BLOCK_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_BLOCK)
	public Object deleteBlocks(@RequestBody List<String> ids) {
			return blockService.deleteByIds(ids);
	}
	
	@ApiOperation(value = "Xóa danh sách Major ra khỏi Block")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MAJOR_DELETE, ERole.Names.ROLE_BLOCK_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_BLOCK_BY_MAJOR)
	public ResponseEntity<?> deleteMajorFromBlock(@PathVariable("id") String blockId, @RequestBody List<String> ids) {
		if(blockService.deleteMajors(blockId, ids)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách Major ra khỏi Block")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_MAJOR_DELETE, ERole.Names.ROLE_BLOCK_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_BLOCK_BY_CLASS)
	public ResponseEntity<?> deleteClassFromBlock(@PathVariable("id") String blockId, @RequestBody List<String> ids) {
		if(blockService.deleteClasses(blockId, ids)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
}
