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
import com.project.smartschool.dto.request.ChapterOfKnowledgeRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.services.KnowledgeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE + ApiConstraint.API_ENPOINT_KNOWLEDGE, tags = "Knowledge Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class KnowledgeController {

	@Autowired
	private KnowledgeService knowledgeService;

	@ApiOperation(value = "Lấy Knowledge theo ID", response = Map.class, responseContainer = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = Map.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(value = ApiConstraint.API_ENPOINT_KNOWLEDGE_ID)
	public Object findOne(@PathVariable String id) {
		return knowledgeService.fetchById(id);
	}
	
	@ApiOperation(value = "Lấy tất cả Knowledge", response = Map.class, responseContainer = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = Map.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(value = ApiConstraint.API_ENPOINT_KNOWLEDGE)
	public Object findAll(@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
			return knowledgeService.fetchAll();
		return knowledgeService.findAll(currentPage, size);
	}
	
	@ApiOperation(value = "Lấy Knowledge theo ID", response = Map.class, responseContainer = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = Map.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PutMapping(value = ApiConstraint.API_ENPOINT_KNOWLEDGE_ID)
	public Object update(@PathVariable String id, @Valid @RequestBody ChapterOfKnowledgeRequest data) {
		return knowledgeService.updateChapter(id, data);
	}
	
	@ApiOperation(value = "Lấy Knowledge theo ID", response = Map.class, responseContainer = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = Map.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(value = ApiConstraint.API_ENPOINT_KNOWLEDGE)
	public Object save(@Valid @RequestBody ChapterOfKnowledgeRequest data) {
		return knowledgeService.saveChapter(data);
	}
	
	@ApiOperation(value = "Xóa kiến thức theo id", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_KNOWLEGDE_DELETE})
	@DeleteMapping(ApiConstraint.API_ENPOINT_KNOWLEDGE_ID)
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if(knowledgeService.deleteById(id)) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách Kiến thức")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_KNOWLEGDE_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_KNOWLEDGE)
	public Object deleteBlocks(@RequestBody List<String> ids) {
			return knowledgeService.deleteByIds(ids);
	}

}
