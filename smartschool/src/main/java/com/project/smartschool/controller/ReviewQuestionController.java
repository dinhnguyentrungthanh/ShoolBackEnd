package com.project.smartschool.controller;

import java.util.List;
import java.util.Map;

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
import com.project.smartschool.dto.request.ReviewQuestionRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.services.ReviewQuestionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_REVIEW_QUESTION, tags = "Review Question Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class ReviewQuestionController {

	@Autowired
	private ReviewQuestionService reviewQuestionService;
	
	@ApiOperation(value = "Lấy tất cả ReviewQuestion", response = Map.class, responseContainer = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = Map.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(value = ApiConstraint.API_ENPOINT_REVIEW_QUESTION)
	public Object findAll(@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size,
			@RequestParam(defaultValue = "") String knowledgeId) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
			return reviewQuestionService.fetchAllByKnowledgeId(knowledgeId);
		return reviewQuestionService.findAllByKnowledgeId(currentPage, size, knowledgeId);
	}
	
	@ApiOperation(value = "Lấy Knowledge theo ID", response = Map.class, responseContainer = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = Map.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(value = ApiConstraint.API_ENPOINT_REVIEW_QUESTION_ID)
	public Object findOne(@PathVariable String id) {
		return reviewQuestionService.fetchById(id);
	}
	
	@ApiOperation(value = "Lấy Knowledge theo ID", response = Map.class, responseContainer = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = Map.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PutMapping(value = ApiConstraint.API_ENPOINT_REVIEW_QUESTION_ID)
	public Object update(@PathVariable String id, @Valid @RequestBody ReviewQuestionRequest data) {
		return reviewQuestionService.updateUsingDTO(data);
	}
	
	@ApiOperation(value = "Cập nhật Câu hỏi", response = Map.class, responseContainer = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = Map.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(value = ApiConstraint.API_ENPOINT_REVIEW_QUESTION)
	public Object save(@Valid @RequestBody ReviewQuestionRequest data) {
		return reviewQuestionService.saveUsingDTO(data);
	}
	
	@ApiOperation(value = "Xóa kiến thức theo id", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_KNOWLEGDE_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_REVIEW_QUESTION_ID)
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(reviewQuestionService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách Câu hỏi")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_KNOWLEGDE_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_REVIEW_QUESTION)
	public Object deleteBlocks(@RequestBody List<String> ids) {
			return reviewQuestionService.deleteByIds(ids);
	}
	
}
