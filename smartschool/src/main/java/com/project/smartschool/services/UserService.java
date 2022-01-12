package com.project.smartschool.services;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.project.smartschool.dto.request.UserRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.UserResponse;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.enums.ELevel;
import com.project.smartschool.enums.ERole;

public interface UserService extends IBaseService<UserEntity, String>, IDtoService<UserResponse, String, UserRequest>, IPageService<UserResponse>{
	
	/**
	 * Truy xuất User thông qua Id
	 * @param id
	 * @return 
	 */
	UserDetails loadUserById(String id);
	
	List<UserEntity> findAllByUsername(Set<String> usernames);
	
	List<UserEntity> findAllByFullname(Set<String> name);
	
	List<UserEntity> findAllByIds(Set<String> ids);
	
	boolean isExistedByUsername(String username);
	
	ObjectResponsePaging<UserResponse> findAll(int page, int size, ELevel level);
	
	UserResponse findByIdOrUsername(String idOrUsername);
	
	UserResponse findByUsername(String username);
	
	int updateCountLogin(String username);
	
	UserResponse register(UserRequest user);
	
	List<UserResponse> fetchAll(ELevel level);
	
	UserResponse saveAvatar(String id, String filename);
	
	boolean changePassword(String id, String oldPassword, String newPassword);
	
	List<UserResponse> fetchAllByClass(String classId, ELevel level);
	
	ObjectResponsePaging<UserResponse> findAllByClass(int page, int size, ELevel level, String classId);
	
	boolean changePasswordByAdmin(String userId, String password);
	
}
