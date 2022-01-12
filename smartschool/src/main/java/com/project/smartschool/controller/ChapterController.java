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
import com.project.smartschool.dto.request.ChapterRequest;
import com.project.smartschool.dto.response.ChapterResponse;
import com.project.smartschool.dto.response.MajorResponse;
import com.project.smartschool.dto.response.MathDesignResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.services.ChapterService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_CHAPTER, tags = "Chapter Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class ChapterController {

	@Autowired
	private ChapterService chapterService;
	
	@ApiOperation(value = "Thêm Chương", response = ChapterResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = ChapterResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_CHAPTER_CREATE})
	@PostMapping(ApiConstraint.API_ENPOINT_CHAPTER)
	public Object insert(@Valid @RequestBody ChapterRequest chapterRequest) throws ValidateException {
		return chapterService.saveUsingDTO(chapterRequest);
	}
	@ApiOperation(value = "cập nhật Chương", response = ChapterResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = ChapterResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_CHAPTER_UPDATE})
	@PutMapping(ApiConstraint.API_ENPOINT_CHAPTER + "/{id}")
	public Object update(@Valid @RequestBody ChapterRequest chapterRequest, @PathVariable String id) throws ValidateException {		
		return chapterService.updateUsingDTO(chapterRequest);
	}
	@ApiOperation(value = "Lấy tất cả các Chương theo paging", response = ChapterResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = ChapterResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_CHAPTER)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
			return chapterService.fetchAll();
		return chapterService.findAll(currentPage, size);
	}
	@ApiOperation(value = "Lấy tất cả các Chương theo id", response = ChapterResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = ChapterResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_CHAPTER + "/{id}")
	public Object getById(@PathVariable String id) {
		return chapterService.findById(id);
	}
	@ApiOperation(value = "Xóa môn theo id", response = ChapterResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = MathDesignResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_CHAPTER_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_CHAPTER + "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(chapterService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách Môn")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_CHAPTER_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_CHAPTER)
	public Object deletechapter(@RequestBody List<String> ids) {
			return chapterService.deleteByIds(ids);
	}
}
