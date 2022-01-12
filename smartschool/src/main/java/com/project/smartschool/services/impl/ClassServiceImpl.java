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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.smartschool.dto.request.ClassRequest;
import com.project.smartschool.dto.response.ClassResponse;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.ClassEntity;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.ClassRepository;
import com.project.smartschool.services.BlockService;
import com.project.smartschool.services.ClassService;
import com.project.smartschool.services.UserService;

@Service
public class ClassServiceImpl implements ClassService{

	@Autowired
	private ClassRepository classRepository;
		
	@Autowired
	private BlockService blockService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClassService classService;
	
	@Override
	public List<ClassEntity> findAll() {
		return classRepository.findAll();
	}

	@Override
	public ClassEntity findById(String id) {
		return classRepository.findById(id).orElse(null);
	}

	@Override
	public ClassEntity save(ClassEntity entity) {
		return classRepository.save(entity);
	}

	@Override
	public List<ClassEntity> saveAll(List<ClassEntity> entities) {		
		return classRepository.saveAll(entities);
	}

	@Override
	public ClassEntity update(ClassEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		ClassEntity entity = verify(id);
		entity.setEnable(false);
		return classRepository.save(entity) != null;
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
	public List<ClassResponse> fetchAll() {
		List<ClassEntity> classes = classRepository.findAllByEnableTrue();
		return classes.stream().map(c -> mappingClassToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public ClassResponse fetchById(String id) {
		ClassEntity classEntity = classRepository.findById(id).orElse(null);
		
		if(ObjectUtils.anyNull(classEntity)) 
			throw new ValidateException("Không tìm thấy Lớp");
		
		return mappingClassToResponse(classEntity);
	}

	private ClassResponse mappingClassToResponse(ClassEntity classEntity) {
		List<UserEntity> users = userService.findAllByIds(classEntity.getUsers());		
		Set<String> userIds = users.stream().map(u -> u.getId()).collect(Collectors.toSet());
		return ClassResponse.builder().build().convertFromEntity(classEntity, userIds);
	}
	
	@Override
	public ClassResponse saveUsingDTO(ClassRequest inputDto) {

		if(classService.isExistedByClassname(inputDto.getClassname().trim())) 
			throw new ValidateException("Tên lớp này đã tồn tại");
		
		return saveOrUpdateUsingDTO(inputDto, inputDto.convertToEntity());
	}

	@Override
	public ClassResponse updateUsingDTO(ClassRequest inputDto) {		
		
		ClassEntity cls = verify(inputDto.getId());
		
		ClassEntity clsAll = classRepository.findByClassname(inputDto.getClassname());

		if(ObjectUtils.allNotNull(cls) && ObjectUtils.allNotNull(clsAll)) {
			if(!inputDto.getId().equals(clsAll.getId()) && inputDto.getClassname().trim().equals(clsAll.getClassname())) {
				throw new ValidateException("Tên Lớp này đã tồn tại");
			}
			
			if(inputDto.getId().equals(clsAll.getId()) && !inputDto.getClassname().trim().equals(clsAll.getClassname())) {
				throw new ValidateException("Tên Lớp này đã tồn tại");
			}
		} 
		
		boolean hasAnyUserNotExisted = inputDto.getUsers().stream().anyMatch(e -> !userService.isExistedById(e));		
		if(hasAnyUserNotExisted) 
			throw new ValidateException("Danh sách tài khoản không hợp lệ");

		return saveOrUpdateUsingDTO(inputDto, classService.findById(inputDto.getId()));
	}
	
	private ClassEntity verify(String id) {
		ClassEntity cls = classRepository.findById(id).orElse(null);
		if (ObjectUtils.anyNull(cls)) {
			throw new ValidateException("Lớp không tồn tại");
		}
		return cls;
	}
	
	private ClassResponse saveOrUpdateUsingDTO(ClassRequest inputDto, ClassEntity classEntity) {
		ClassEntity cls = classService.save(inputDto.convertToEntity(classEntity));
		
		// Update user
		List<UserEntity> usersUpdated = inputDto.getUsers().stream().map(e -> mappingResquestToEntity(e, cls)).collect(Collectors.toList());
		List<UserEntity> usersSaved = userService.saveAll(usersUpdated);
		
		// Update Block
		BlockEntity block = blockService.findById(inputDto.getBlock());
		Set<String> blocksUpdated = block.getClasses();
		blocksUpdated.add(cls.getId());		
		block.setClasses(blocksUpdated);		
		blockService.save(block);
		
		Set<String> userIds = usersSaved.stream().map(u -> u.getId()).collect(Collectors.toSet());

		return ClassResponse.builder().build().convertFromEntity(classEntity, userIds);
	}
	
	private UserEntity mappingResquestToEntity(String userId, ClassEntity cls) {
		UserEntity u = userService.findById(userId);		
		Set<String> classes = u.getClasses() == null ? new HashSet<String>() : u.getClasses();			
		classes.add(cls.getId());			
		u.setClasses(classes);
		return u;
	}

	@Override
	public boolean isExistedById(String id) {
		return classRepository.existsById(id);
	}

	@Override
	public boolean isExistedByClassname(String classname) {
		return !ObjectUtils.anyNull(classRepository.findByClassname(classname));
	}

	@Override
	public ObjectResponsePaging<ClassResponse> findAll(int page, int size) {
		Page<ClassEntity> pages = this.classRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<ClassResponse> elements = pages.getContent().stream().map(c -> mappingClassToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<ClassResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public ObjectResponsePaging<ClassResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClassEntity> findAllByIds(Set<String> ids) {
		return StreamSupport.stream(classRepository.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ClassEntity> findAllByIds(List<String> ids) {
		return StreamSupport
				  .stream(classRepository.findAllById(ids).spliterator(), false)
				  .collect(Collectors.toList());
	}

	@Transactional
	@Override
	public Map<String, Boolean> deleteUsersFromClassByIds(String classId, List<String> ids) {
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		
		ClassEntity cls = verify(classId);
		
		Set<String> userIds = cls.getUsers();
		
		userIds.removeAll(ids);
		
		cls.setUsers(userIds);
		
		classRepository.save(cls);
		
		List<UserEntity> users = userService.findAllByIds(ids);
		
		users.stream().forEach(u -> {			
			try {		
				Set<String> classIds  = u.getClasses();
				classIds.remove(cls.getId());				
				u.setClasses(classIds);				
				userService.save(u);
				m.put(u.getId(), true);
			} catch (Exception e) {
				m.put(u.getId(), false);
			}
		});
		return m;
	}
}
