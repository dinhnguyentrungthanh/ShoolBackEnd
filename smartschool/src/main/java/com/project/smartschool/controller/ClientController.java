package com.project.smartschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartschool.constraints.ApiConstraint;
import com.project.smartschool.constraints.PagingContraint;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.dto.response.UserResponse;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.services.ClientQueryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class ClientController {
	
	
	@Autowired
	private ClientQueryService clientQueryService;
	
	@ApiOperation(value = "Lấy tất cả các Chương", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_CHAPTER_F_BLOCK_F_MAJOR_F_MATHDESIGN)
	public Object getAllWithPage(
			@PathVariable String blockSeo,
			@PathVariable String majorSeo,
			@PathVariable String mathdesignSeo	
			) {		
		return clientQueryService.fetchAllChaptersFromBlockAndMajorAndMathdesign(blockSeo, majorSeo, mathdesignSeo);
	}
	@ApiOperation(value = "Lấy dánh sách knowledge", response = BlockResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = BlockResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_CHAPTER_F_BLOCK_F_MAJOR_F_MATHDESIGN_F_CHAPTER_F_KNOWLEDGE)
	public Object getAllWithKnowledge(
			@PathVariable String blockSeo,
			@PathVariable String majorSeo,
			@PathVariable String mathdesignSeo,	
			@PathVariable String chapterSeo,
			@PathVariable String knowledgeSeo	
			) {		
		return clientQueryService
				.fetchAllChaptersFromBlockAndMajorAndMathdesignAndChapterAndKnowledge(
						blockSeo, 
						majorSeo, 
						mathdesignSeo,
						chapterSeo,
						knowledgeSeo);
	}
	
//	@ApiOperation(value = "Lấy theo Username", response = UserEntity.class)
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class),
//			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
//			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
//			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
//	@GetMapping(ApiConstraint.API_ENPOINT_USERNAME )
//	public Object getUsernameByUser(@PathVariable String usernameSeo) {
//		return clientQueryService.findByUsernameUser(usernameSeo);
//	}
	
	@ApiOperation(value = "Lấy theo id", response = UserEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_ID )
	public Object getId(@PathVariable String idSeo) {
		return clientQueryService.findByIdUsername(idSeo);
	}

}
