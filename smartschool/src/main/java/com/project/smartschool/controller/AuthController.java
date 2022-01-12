package com.project.smartschool.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.smartschool.constraints.ApiConstraint;
import com.project.smartschool.constraints.PagingContraint;
import com.project.smartschool.dto.CustomUserDetails;
import com.project.smartschool.dto.request.LoginRequest;
import com.project.smartschool.dto.request.RefreshToken;
import com.project.smartschool.dto.request.UserRequest;
import com.project.smartschool.dto.response.ClassResponse;
import com.project.smartschool.dto.response.UserResponse;
import com.project.smartschool.entities.RoleEntity;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.ApiError;
import com.project.smartschool.security.JwtTokenProvider;
import com.project.smartschool.services.ClassService;
import com.project.smartschool.services.RoleGroupService;
import com.project.smartschool.services.RoleService;
import com.project.smartschool.services.UserService;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(ApiConstraint.API_BASE)
public class AuthController {

//	@Autowired
//	private JdbcTemplate jdbc;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager auth;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ClassService  classService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;


	@PostMapping(ApiConstraint.API_ENPOINT_AUTH_LOGIN)
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		Authentication authentication = auth.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
		response.addCookie(new Cookie("session", token));
		Map<String, String> result = new HashMap<>();
		result.put("token", token);
		result.put("username", loginRequest.getUsername());
		return ResponseEntity.ok(Collections.singletonMap("detail", result));
	}
	
	@PostMapping(ApiConstraint.API_ENPOINT_AUTH_REFRESH)
	public ResponseEntity<?> refreshToken(@RequestBody RefreshToken refreshToken,@RequestAttribute Claims claims , HttpServletResponse response) {
		UserResponse user = userService.fetchById(String.valueOf(claims.get("id")));
		String token = tokenProvider.generateToken(user);
		response.addCookie(new Cookie("session", token));
		Map<String, String> result = new HashMap<>();
		result.put("token", token);
		result.put("username", user.getUsername());
		return ResponseEntity.ok(Collections.singletonMap("detail", result));
	}

	
	
	@ApiOperation(value = "Đăng kí tài khoản", response = UserEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = UserResponse.class),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@PostMapping(ApiConstraint.API_ENPOINT_AUTH_REGISTER)
	public UserResponse register(@Valid @RequestBody UserRequest user) {
		return userService.register(user);
	}
	@GetMapping("/auth/genrole")
	public Object gen() {
		Set<ERole> roles = roleService.findAll().stream().map(e -> {			
			return e.getName();
		}).collect(Collectors.toSet());
		
		List<ERole> list = Arrays.asList(ERole.values());
		
		List<ERole> newRoles = list.stream().filter(e -> !roles.contains(e)).collect(Collectors.toList());
		for(ERole e : newRoles) {
			RoleEntity r = new RoleEntity();
			r.setCode(e.getCode());
			r.setName(e);
			roleService.save(r);
		}
		
		return roleService.findAll();
	}
	
	@ApiOperation(value = "Lấy tất cả các lớp theo paging", response = ClassResponse.class, responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "SUCCESS", response = ClassResponse.class, responseContainer = "List"),
			@ApiResponse(code = 401, message = "UNAUTHORIZE", response = ApiError.class),
			@ApiResponse(code = 403, message = "FORBIDDEN", response = ApiError.class),
			@ApiResponse(code = 404, message = "NOT FOUND", response = ApiError.class) })
	@GetMapping(ApiConstraint.API_ENPOINT_AUTH_CLASS)
	public Object getAllWithPage(
			@RequestParam(defaultValue = PagingContraint.DEFAULT_ENABLE_PAGING) String isPaging,
			@RequestParam(defaultValue = PagingContraint.DEFAULT_PAGE) int currentPage, 
			@RequestParam(defaultValue = PagingContraint.DEFAULT_SIZE) int size) {
		if(isPaging.equals(PagingContraint.DEFAULT_DISABLE_PAGING))
			return classService.fetchAll();
		return classService.findAll(currentPage, size);
	}

}
