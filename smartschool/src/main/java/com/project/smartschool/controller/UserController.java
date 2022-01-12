package com.project.smartschool.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.project.smartschool.dto.request.ChangePasswordRequest;
import com.project.smartschool.dto.request.UserChangePassRequest;
import com.project.smartschool.dto.request.UserRequest;
import com.project.smartschool.dto.request.UserRequestInsert;
import com.project.smartschool.dto.response.UserResponse;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.enums.ELevel;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@ApiOperation(value = ApiConstraint.API_BASE  + ApiConstraint.API_ENPOINT_USER, tags = "User Profile Controller")
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Lấy tất cả người dùng", response = UserResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })	
	@GetMapping(ApiConstraint.API_ENPOINT_USER)
	public Object getAllUser(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size,
			@RequestParam ELevel level,
			@RequestParam(defaultValue = "") String classId) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING)) {
			if(StringUtils.isNotBlank(classId)) {
				return userService.fetchAllByClass(classId, level);
			}
			return userService.fetchAll(level);
		}
		
		if(StringUtils.isNotBlank(classId)) {
			return userService.findAllByClass(currentPage, size, level, classId);
		}
		return userService.findAll(currentPage, size, level);
	}

	@ApiOperation(value = "Lấy theo Username", response = UserEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_USER + "/{id}")
	public UserResponse getUser(@PathVariable("id") String idOrUsername) {
		return userService.fetchById(idOrUsername);
	}
	
	@ApiOperation(value = "Lấy theo Username", response = UserEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PutMapping(ApiConstraint.API_ENPOINT_USER_COUNT)
	public ResponseEntity<?> getUserByUsername(@PathVariable("id") String username) {
		return new ResponseEntity<>(userService.updateCountLogin(username), HttpStatus.ACCEPTED);
	}
	
	@ApiOperation(value = "Tạo User", response = UserEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(ApiConstraint.API_ENPOINT_USER)
	public UserResponse createUser(@Valid @RequestBody UserRequestInsert user) {
		return userService.saveUsingDTO(user);
	}
	
	@ApiOperation(value = "Cập nhật User", response = UserEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PutMapping(ApiConstraint.API_ENPOINT_USER_ID )
	public UserResponse updateUser(@PathVariable String id, @Valid @RequestBody UserRequest user) {
		user.setId(id);
		return userService.updateUsingDTO(user);
	}
	
	@ApiOperation(value = "Xóa User")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_USER_DELETE})
	@DeleteMapping(value = ApiConstraint.API_ENPOINT_USER + "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
			if(userService.deleteById(id)) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "Xóa danh sách User")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@Secured({ERole.Names.ROLE_USER_DELETE})
	@PatchMapping(value = ApiConstraint.API_ENPOINT_USER)
	public Object deleteUsers(@RequestBody List<String> ids) {
			return userService.deleteByIds(ids);
	}
	
	@ApiOperation(value = "Changepass")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PutMapping(value = ApiConstraint.API_ENPOINT_CHANGE_PASSWORD)
	public boolean changePassword(
			@RequestBody ChangePasswordRequest changepws) {
			 return userService.changePassword(changepws.getId(),changepws.getOldPassword(),changepws.getNewPassword());
	}
	
	@ApiOperation(value = "Change passwoord By Admin ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PutMapping(value = ApiConstraint.API_ENPOINT_CHANGE_PASSWORD_ADMIN)
	public boolean changePasswordByAdmin(
			@RequestBody UserChangePassRequest changepws) {
			 return userService.changePasswordByAdmin(changepws.getUser(),changepws.getPassword());
	}
}
