package com.project.smartschool.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.smartschool.dto.CustomUserDetails;
import com.project.smartschool.dto.request.PointRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.PointResponse;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.PointEntity;
import com.project.smartschool.entities.TestEntity;
import com.project.smartschool.entities.TestTypeEntity;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.enums.ELevel;
import com.project.smartschool.enums.ETestType;
import com.project.smartschool.errors.GlobalException;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.helpers.classes.TestResult;
import com.project.smartschool.repository.PointRepository;
import com.project.smartschool.services.PointService;
import com.project.smartschool.services.TestService;
import com.project.smartschool.services.TestTypeService;
import com.project.smartschool.services.UserService;

@Service
public class PointServiceImpl implements PointService {

	@Autowired
	private PointRepository pointRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TestTypeService testTypeService;
	
	@Autowired
	private TestService testService;
	
	@Override
	public List<PointEntity> findAll() {
		return null;
	}

	@Override
	public List<PointEntity> findAllByIds(List<String> ids) {
		return null;
	}

	@Override
	public PointEntity findById(String id) {
		return pointRepository.findById(id).orElse(null);
	}

	@Override
	public PointEntity save(PointEntity entity) {
		return pointRepository.save(entity);
	}

	@Override
	public List<PointEntity> saveAll(List<PointEntity> entities) {
		return  pointRepository.saveAll(entities);
	}

	@Override
	public PointEntity update(PointEntity entity) {
		return pointRepository.save(entity);
	}
	
	private PointEntity verify(String id) {
		PointEntity point = findById(id);
		if(ObjectUtils.anyNull(point)) {
			throw new ValidateException("Not found Point Id");
		}
		return point;
	}

	@Override
	public boolean deleteById(String id) {
		PointEntity entity = verify(id);
		entity.setEnable(false);
		pointRepository.save(entity);
		return true;
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Boolean> deleteByIds(List<String> ids) {
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for(String id: ids) {
			try {
				deleteById(id);
				m.put(id, true);			
			} catch (Exception e) {
				m.put(id, false);
			}
		}
		return m;
	}

	@Override
	public boolean isExistedById(String id) {
		return pointRepository.existsById(id);
	}

	@Override
	public List<PointResponse> fetchAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PointResponse fetchById(String id) {
		PointEntity pointEntity = pointRepository.findById(id).orElse(null);

		if (ObjectUtils.anyNull(pointEntity))
			throw new ValidateException("Không tìm thấy Điểm");

		return mappingPointToResponse(pointEntity);
	}

	@Override
	public PointResponse saveUsingDTO(PointRequest inputDto) {
		try {			
			
			TestTypeEntity testType = testTypeService.findById(inputDto.getTestType());
			
			if (ObjectUtils.anyNull(testType)) {
				throw new ValidateException("Can not found TestType Id");
			}
			
			UserEntity user = userService.findById(inputDto.getUser());
			
			if (ObjectUtils.anyNull(user)) {
				throw new ValidateException("Can not found User Id");
			}
			
			PointEntity entity = pointRepository.save(inputDto.convertToEntity());
			
			entity.setType(testType.getType());
			entity.setCompleted(false);
			
			return mappingPointToResponse(entity);
		} catch (Exception e) {
			throw new GlobalException(e.getLocalizedMessage());
		}
	}

	@Transactional
	@Override
	public PointResponse updateUsingDTO(PointRequest inputDto) {
		try {	
			TestTypeEntity testType = testTypeService.findById(inputDto.getTestType());
			
			if (ObjectUtils.anyNull(testType)) {
				throw new ValidateException("Can not found TestType Id");
			}
			
			UserEntity user = userService.findById(inputDto.getUser());
			
			if (ObjectUtils.anyNull(user)) {
				throw new ValidateException("Can not found User Id");
			}
			
			PointEntity beforeSave = findById(inputDto.getId());
			
			if(testType.getType().compareTo(ETestType.MULTI_CHOICE) == 0) {
				beforeSave = handlerForMultiChoice(inputDto.convertToEntity(beforeSave));
			} else {
				beforeSave = handlerForEssay(inputDto.convertToEntity(beforeSave));
			}
			beforeSave.setType(testType.getType());
			
			PointEntity entity = save(beforeSave);
			
			return mappingPointToResponse(entity);
		} catch (Exception e) {
			throw new GlobalException(e.getLocalizedMessage());
		}
	}
	
	private PointEntity handlerForMultiChoice(PointEntity entity) {
		List<TestResult> testMemo = entity.getTestMemo().stream().map(e -> {
			TestEntity test = testService.findById(e.getId());
			e.setAnswerCorrect(test.getAnswerCorrect());
			return e;
		}).collect(Collectors.toList());
		
		entity.setTestMemo(testMemo);
		entity.calculatePoint();
		entity.setCompleted(true);
		
		return entity;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	private PointEntity handlerForEssay(PointEntity entity) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
		boolean isTeacher = !(user.getUser().getLevel().compareTo(ELevel.STUDENT) == 0);
		if(isTeacher) {
			entity.setCompleted(true);
		}
		return entity;
	}
	
	private PointResponse mappingPointToResponse(PointEntity test) {
		try {

			TestTypeEntity testType = testTypeService.findById(test.getTestType());
			
			if (ObjectUtils.anyNull(testType)) {
				throw new ValidateException("Can not found TestType Id");
			}
			
			UserEntity user = userService.findById(test.getUser());
			
			if (ObjectUtils.anyNull(user)) {
				throw new ValidateException("Can not found User Id");
			}			
			
			return PointResponse.builder().build().convertFromEntity(test, user, testType);
		} catch (Exception e) {
			throw new GlobalException("Dữ liệu không hợp lệ! Vui lòng thử lại.");
		}
	}
	

	@Override
	public ObjectResponsePaging<PointResponse> findAll(int page, int size) {
		Page<PointEntity> pages = pointRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<PointResponse> elements = pages.getContent().stream().map(c -> mappingPointToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<PointResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public ObjectResponsePaging<PointResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		return null;
	}

	@Transactional
	@Override
	public void updateTestResultStream(String id, PointRequest pointRequest) {
		PointEntity point = verify(id);
		point.setTestMemo(pointRequest.getTestMemo());		
		
		TestTypeEntity testType = testTypeService.findById(point.getTestType());
		
		if (ObjectUtils.anyNull(testType)) {
			throw new ValidateException("Can not found TestType Id");
		}
		
		UserEntity user = userService.findById(point.getUser());
		
		if (ObjectUtils.anyNull(user)) {
			throw new ValidateException("Can not found User Id");
		}
		
		if(testType.getType().compareTo(ETestType.MULTI_CHOICE) == 0) {
			point = handlerForMultiChoice(pointRequest.convertToEntity(point));
		} else {
			point = handlerForEssay(pointRequest.convertToEntity(point));
		}
		
		save(point);		
	}

	@Override
	public ObjectResponsePaging<PointResponse> findAllByUser(int page, int size, String user) {
		Page<PointEntity> pages = pointRepository.findAllByEnableTrueAndUser(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"), user);
		List<PointResponse> elements = pages.getContent().stream().map(c -> mappingPointToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<PointResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public List<PointResponse> fetchAllByUser(String user) {
		List<PointEntity> ps = pointRepository.findByEnableTrueAndUser(user);
		return ps.stream().map(p -> mappingPointToResponse(p)).collect(Collectors.toList());
	}

	@Override
	public ObjectResponsePaging<PointResponse> findAllByType(int page, int size, String type) {
		Page<PointEntity> pages = pointRepository.findAllByEnableTrueAndType(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"), type);
		List<PointResponse> elements = pages.getContent().stream().map(c -> mappingPointToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<PointResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public List<PointResponse> fetchAllByType(String type) {
		List<PointEntity> ps = pointRepository.findByEnableTrueAndType(type);
		return ps.stream().map(p -> mappingPointToResponse(p)).collect(Collectors.toList());
	}

}
