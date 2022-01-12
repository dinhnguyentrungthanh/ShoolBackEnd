package com.project.smartschool.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.smartschool.constraints.ApiConstraint;
import com.project.smartschool.dto.response.UserResponse;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.services.FileStorageService;
import com.project.smartschool.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_USER, tags = "User Profile Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class UploadFileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Value("${my.domain}")
	private String domain;
	
	@ApiOperation(value = "Upload avatar", response = UserEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(ApiConstraint.API_ENPOINT_UPLOAD_MEDIA_AVATAR)
	public UserResponse createUser(@PathVariable("id") String userId, @RequestParam MultipartFile avatar) throws IOException {
		String filename = fileStorageService.saveAvatar(avatar);
		return userService.saveAvatar(userId, filename);
	}
	
	@ApiOperation(value = "Upload avatar", response = UserEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(ApiConstraint.API_ENPOINT_UPLOAD_MEDIA_FILE)
	public Object uploadImage(@RequestParam MultipartFile upload) throws IOException {
		Map<String, String> res = new HashMap<String, String>();
		String filename = fileStorageService.saveImg(upload);
		res.put("url", domain.concat("/assets/media/img/").concat(filename));
		return res;
	}
	
}
