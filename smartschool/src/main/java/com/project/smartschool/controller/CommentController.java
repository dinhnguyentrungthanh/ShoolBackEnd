package com.project.smartschool.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartschool.constraints.ApiConstraint;
import com.project.smartschool.dto.request.CommentRequest;
import com.project.smartschool.dto.response.CommentReponse;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.services.CommentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_COMMENT, tags = "User Profile Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@ApiOperation(value = "Thêm Comment", response = CommentReponse.class, responseContainer = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = CommentReponse.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Transactional
	@PostMapping(value = ApiConstraint.API_ENPOINT_COMMENT)
	public Map<String, Object> insert(@Valid @RequestBody CommentRequest commentRequest) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			result.put("result", commentService.saveUsingDTO(commentRequest));
			result.put("isError", false);
			result.put("message", "Lưu dữ liệu thành công!");
		} catch (Exception e) {
			result.put("isError", false);
			result.put("message", "Lưu dữ liệu thất bại!");
		}
		
		return result;
	}
	
	@ApiOperation(value = "Sửa Comment", response = CommentReponse.class, responseContainer = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = CommentReponse.class, responseContainer = ""),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Transactional
	@PutMapping(value = ApiConstraint.API_ENPOINT_COMMENT + "/{id}")
	public Map<String, Object> update(@Valid @RequestBody CommentRequest commentRequest, @PathVariable String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		commentRequest.setId(id);
		
		try {
			result.put("result", commentService.updateUsingDTO(commentRequest));
			result.put("isError", false);
			result.put("message", "Lưu dữ liệu thành công!");
		} catch (Exception e) {
			result.put("isError", false);
			result.put("message", "Lưu dữ liệu thất bại!");
		}
		
		return result;
	}
	
	@ApiOperation(value = "Lất tất cả Comment", response = CommentReponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = CommentReponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(value = ApiConstraint.API_ENPOINT_COMMENT + "/list")
	public Map<String, Object> findAll(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String testId = request.getParameter("testId");
		String parrentId = request.getParameter("parrentId");
		
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer size = Integer.parseInt(request.getParameter("size"));
		
		try {
			result.put("list", commentService.findAll(testId, parrentId, page - 1, size));
			result.put("isError", false);
			result.put("message", "");
		} catch (Exception e) {
			result.put("isError", true);
			result.put("message", "Có lỗi trong quá trình xử lý!");
		}

		return result;
	}
	
}
