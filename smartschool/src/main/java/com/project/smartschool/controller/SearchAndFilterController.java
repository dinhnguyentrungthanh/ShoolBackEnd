package com.project.smartschool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartschool.constraints.ApiConstraint;
import com.project.smartschool.dto.request.KnowledgeFilterRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.dto.response.ChapterResponse;
import com.project.smartschool.dto.response.MathDesignResponse;
import com.project.smartschool.entities.KnowledgeEntity;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.services.SearchAndFilterService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class SearchAndFilterController {
	
	@Autowired
	private SearchAndFilterService searchAndFilterService;
	
	@ApiOperation(value = "Lấy tất cả các Dạng theo Khối", response = MathDesignResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(ApiConstraint.API_ENDPOINT_SEARCH_MATHDESIGN_FROM_BLOCK)
	public Object getAllMathDesignFromBlocks(@RequestBody List<String> blockIds) {
		return searchAndFilterService.fetchAllMathDesignFromBlocks(blockIds);
	}
	
	@ApiOperation(value = "Lấy tất cả các Chương theo Dạng", response = ChapterResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(ApiConstraint.API_ENDPOINT_SEARCH_CHAPTER_FROM_MATHDESIGN)
	public Object getAllChapterFromMathDesigns(@RequestBody List<String> mdIds) {
		return searchAndFilterService.fetchAllChapterFromMathDesign(mdIds);
	}
	
	
	@ApiOperation(value = "Lấy tất cả các Kiến Thức dựa vào filter", response = KnowledgeEntity.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(ApiConstraint.API_ENDPOINT_SEARCH_KNOWLEDGE_BY_FILTER)
	public Object searchKnowledge(@RequestBody KnowledgeFilterRequest filter) {
		System.err.println(filter.getKeywords());
		return searchAndFilterService.searchKnowLedge(filter);
	}
}
