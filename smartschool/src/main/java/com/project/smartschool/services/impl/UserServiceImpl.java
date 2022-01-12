package com.project.smartschool.services.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.smartschool.dto.CustomUserDetails;
import com.project.smartschool.dto.request.UserRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.UserResponse;
import com.project.smartschool.entities.ClassEntity;
import com.project.smartschool.entities.RoleEntity;
import com.project.smartschool.entities.RoleGroupEntity;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.enums.ELevel;
import com.project.smartschool.enums.ERole;
import com.project.smartschool.errors.NotFoundException;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.ClassRepository;
import com.project.smartschool.repository.RoleGroupRepository;
import com.project.smartschool.repository.RoleRepository;
import com.project.smartschool.repository.UserRepository;
import com.project.smartschool.services.ClassService;
import com.project.smartschool.services.RoleGroupService;
import com.project.smartschool.services.UserService;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleGroupRepository roleGroupRepository;
	
	@Autowired 
	private RoleGroupService roleGroupService;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private ClassService classService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		// Kiểm tra xem user có tồn tại trong database không?
		UserEntity user = userRepository.findByUsernameAndEnableTrue(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return handlerRolesForSecurity(user);
	}

	@Transactional
	@Override
	public UserDetails loadUserById(String username) {
		UserEntity user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username : " + username);
		}
		
		return handlerRolesForSecurity(user);
	}
	
	private UserDetails handlerRolesForSecurity(UserEntity user) {
		Set<String> roleGroups = user.getRoleGroups();
		
		List<RoleGroupEntity> rg = roleGroups.stream().map(r -> roleGroupService.findById(r)).collect(Collectors.toList());
		
		Set<String> roleIds = new HashSet<String>();
		
		rg.stream().forEach(r -> roleIds.addAll(r.getRoles()));
		
		Set<ERole> roleNames = roleIds.stream().map(r -> {	
			RoleEntity role = roleRepository.findById(r).orElse(null);
			return role.getName();
		}).collect(Collectors.toSet());
		
		return new CustomUserDetails(user, roleNames, initRole(roleNames));
	}
	
	 private List<GrantedAuthority> initRole(Set<ERole> roles){  
//	        if(roles.size() > 0 ) {
//	        	return roles.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
//	        }
	        return roles.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
	       // throw new BadCredentialsException("USERNAME OR PASSWORD IS WRONG!");
	    }


	@Override
	public List<UserEntity> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public UserEntity findById(String id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public UserEntity save(UserEntity entity) {
		return userRepository.save(entity);
	}

	@Override
	public UserEntity update(UserEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		UserEntity user = userRepository.findByIdAndEnableTrue(id);
		if (ObjectUtils.anyNull(user)) {
			throw new ValidateException("Tài khoản không tồn tại");
		}
		user.setEnable(false);
		userRepository.save(user);
		return true;
	}

	@Override
	public Map<String, Boolean> deleteByIds(List<String> ids) {
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for(String id: ids) {
			try {
				this.deleteById(id);
				m.put(id, true);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				m.put(id, false);
			}
		}
		return m;
	}

	@Override
	public List<UserResponse> fetchAll() {
		return userRepository.findAll().stream().map(this::mappingClassToResponse).collect(Collectors.toList());
	}

	@Override
	public UserResponse fetchById(String id) {
		UserEntity user = userRepository.findByIdAndEnableTrue(id);
		if (user == null) {
			throw new ValidateException("Tài khoản không tồn tại");
		}
		return mappingClassToResponse(user);
	}
	
	private UserResponse mappingClassToResponse(UserEntity user) {

		// Lấy tất cả RoleGroup
		Iterable<RoleGroupEntity> rgroupsIterable = roleGroupRepository.findAllById(user.getRoleGroups());
		List<RoleGroupEntity> rgroups = StreamSupport.stream(rgroupsIterable.spliterator(), false)
				.collect(Collectors.toList());
		
		Set<String> rgs = rgroups.stream().map(RoleGroupEntity::getId).collect(Collectors.toSet());

		// Loại bỏ tất cả role của RoleGroup nếu trùng nhau
		Set<String> roleIds = new HashSet<>();
		for (RoleGroupEntity rgroup : rgroups) {
			roleIds.addAll(rgroup.getRoles());
		}

		// Lấy tất cả name của Role thuộc tất cả RoleGroup
		Set<String> roles = StreamSupport.stream(roleRepository.findAllById(roleIds).spliterator(), false)
				.map(e -> e.getName().toString()).collect(Collectors.toSet());
		
		// Lấy tất cả classes
		Set<String> classes = StreamSupport.stream(classRepository.findAllById(user.getClasses()).spliterator(), false)
				.map(ClassEntity::getId).collect(Collectors.toSet());

		// Binding data và convert to UserResponse
		return UserResponse.builder().build().convertFromEntity(user, rgs, roles, classes);
	}

	@Override
	public UserResponse saveUsingDTO(UserRequest inputDto) {
		if(this.isExistedByUsername(inputDto.getUsername()))
			throw new ValidateException("Tên Tài khoản đã tồn tại");
		
		return saveOrUpdateUsingDTO(inputDto, inputDto.convertToEntity());
	}

	@Override
	public UserResponse updateUsingDTO(UserRequest inputDto) {
		UserEntity user = userRepository.findByUsernameAndEnableTrue(inputDto.getUsername());
		
		if(ObjectUtils.allNotNull(user) && !user.getId().equals(inputDto.getId()))
			throw new ValidateException("Tên Tài khoản đã tồn tại");
		
		Set<String> classOld = user.getClasses();
		Set<String> classNew = inputDto.getClasses();
		
		Set<String> classRemoved = classOld.stream().filter(c -> !classNew.contains(c)).collect(Collectors.toSet());
		
		classService.findAllByIds(classRemoved).stream().forEach(c -> {
			c.getUsers().remove(inputDto.getId());
			classService.save(c);
		});
		
		return saveOrUpdateUsingDTO(inputDto, inputDto.convertFromEntity(user));
	}
	
	private UserResponse saveOrUpdateUsingDTO(UserRequest inputDto, UserEntity userEntity) {
		
		UserEntity user = userRepository.save(userEntity);
		
		List<ClassEntity> classes = classService.findAllByIds(inputDto.getClasses()).stream().map(c -> {
			Set<String> users = c.getUsers();
			users.add(user.getId());
			c.setUsers(users);
			return c;
		}).collect(Collectors.toList());
		classService.saveAll(classes);
		return mappingClassToResponse(user);
	}

	@Override
	public List<UserEntity> saveAll(List<UserEntity> entities) {
		return userRepository.saveAll(entities);
	}

	@Override
	public List<UserEntity> findAllByUsername(Set<String> ids) {		
		return ids.stream().map(e -> userRepository.findByUsernameAndEnableTrue(e)).collect(Collectors.toList());
	}

	@Override
	public List<UserEntity> findAllByFullname(Set<String> name) {
		return name.stream().map(e -> userRepository.findByFullnameAndEnableTrue(e)).collect(Collectors.toList());
	}

	@Override
	public boolean isExistedById(String id) {
		return userRepository.existsById(id);
	}

	@Override
	public List<UserEntity> findAllByIds(Set<String> ids) {
		return StreamSupport.stream(userRepository.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public ObjectResponsePaging<UserResponse> findAll(int page, int size) {
		Page<UserEntity> pages = this.userRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<UserResponse> elements = pages.getContent().stream().map(c -> mappingClassToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<UserResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public ObjectResponsePaging<UserResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		return null;
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExistedByUsername(String username) {
		return !ObjectUtils.anyNull(userRepository.findByUsername(username));
	}

	@Override
	public ObjectResponsePaging<UserResponse> findAll(int page, int size, ELevel level) {
		Page<UserEntity> pages = this.userRepository.findAllByEnableTrueAndLevel(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"), level);
		List<UserResponse> elements = pages.getContent().stream().map(c -> mappingClassToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<UserResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}
	
	public UserResponse findByIdOrUsername(String idOrUsername) {
		UserEntity user = userRepository.findByIdOrUsernameAndEnableTrue(idOrUsername);
		if (user == null) {
			throw new ValidateException("Tài khoản không tồn tại");
		}
		return mappingClassToResponse(user);
	}

	@Override
	public List<UserEntity> findAllByIds(List<String> ids) {
		return StreamSupport.stream(userRepository.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public UserResponse findByUsername(String username) {
		UserEntity user = userRepository.findByUsername(username);
		if (user == null) {
			throw new ValidateException("Tài khoản không tồn tại");
		}
		return mappingClassToResponse(user);
	}

	@Override
	public int updateCountLogin(String username) {
		UserEntity user = userRepository.findByUsername(username);
		int countDb = user.getCount() + 1;		
		user.setCount(countDb);
		return userRepository.save(user).getCount();
	}

	@Override
	public UserResponse register(UserRequest user) {
		if(this.isExistedByUsername(user.getUsername()))
			throw new ValidateException("Tên Tài khoản đã tồn tại");
		
		return saveOrUpdateUsingDTO(user, user.convertToEntity());
	}

	@Override
	public List<UserResponse> fetchAll(ELevel level) {
		List<UserEntity> users = userRepository.findByLevelAndEnableTrue(level);
		return users.stream().map(c -> mappingClassToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public UserResponse saveAvatar(String id, String filename) {
		UserEntity user = findById(id);
		
		if(ObjectUtils.anyNull(user) || !user.isEnable()) {
			throw new NotFoundException("Not found user");
		}		
		user.setAvatar(filename);
		return UserResponse.builder().build().convertFromEntity(save(user));
	}

	@Override
	public boolean changePassword(String id, String oldPassword, String newPassword) {
		UserEntity user = userRepository.findById(id).orElse(null);
		if(ObjectUtils.anyNull(user) || !user.isEnable()) {
			throw new NotFoundException("Not found user");
		}		
		
		boolean isValidOldPassword = BCrypt.checkpw(oldPassword, user.getPassword());
		
		if(!isValidOldPassword) {
			throw new ValidateException("Mật khẩu không đúng");
		}

		user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
		userRepository.save(user);		
		return true;
	}

	@Override
	public List<UserResponse> fetchAllByClass(String classId, ELevel level) {
		
		ClassEntity cls = classService.findById(classId);
		
		if(ObjectUtils.anyNull(cls)) {
			throw new ValidateException("Not found Class Id");
		}
		
		List<UserEntity> users = findAllByIds(cls.getUsers()).stream().filter(u -> u.getLevel().equals(ELevel.STUDENT) && u.isEnable()).collect(Collectors.toList());
		return users.stream().map(c -> mappingClassToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public ObjectResponsePaging<UserResponse> findAllByClass(int page, int size, ELevel level, String classId) {
		ClassEntity cls = classService.findById(classId);
		
		if(ObjectUtils.anyNull(cls)) {
			throw new ValidateException("Not found Class Id");
		}	
		
		Page<UserEntity> pages = userRepository.findAllByEnableTrueAndLevelAndIdIn(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"), level, cls.getUsers());
		List<UserResponse> elements = pages.getContent().stream().map(c -> mappingClassToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<UserResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public boolean changePasswordByAdmin(String userId, String password) {
		UserEntity user = userRepository.findById(userId).orElse(null);
		if(ObjectUtils.anyNull(user) || !user.isEnable()) {
			throw new NotFoundException("Not found user");
		}		

		user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		userRepository.save(user);		
		return true;
	}
}
