package com.project.smartschool.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.smartschool.dto.request.TestRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.TestResponse;
import com.project.smartschool.entities.TestEntity;
import com.project.smartschool.entities.TestTypeEntity;
import com.project.smartschool.errors.GlobalException;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.TestRepository;
import com.project.smartschool.services.TestService;
import com.project.smartschool.services.TestTypeService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestRepository testRepository;

	@Autowired
	private TestTypeService testTypeService;

	@Override
	public List<TestEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TestEntity> findAllByIds(List<String> ids) {
		return StreamSupport.stream(testRepository.findAllById(ids).spliterator(), false).filter(e -> e.isEnable()).collect(Collectors.toList());
	}

	@Override
	public TestEntity findById(String id) {
		return testRepository.findById(id).orElse(null);
	}

	@Override
	public TestEntity save(TestEntity entity) {
		return testRepository.save(entity);
	}

	@Override
	public List<TestEntity> saveAll(List<TestEntity> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestEntity update(TestEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		TestEntity test = findById(id);
		
		if(ObjectUtils.anyNull(test)) {
			throw new ValidateException("Can not found Test ID");
		}
		
		test.setEnable(false);	
		
		return save(test) != null;
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
				this.deleteById(id);
				m.put(id, true);
			} catch (Exception e) {
				m.put(id, false);
			}
		}
		return m;
	}

	@Override
	public boolean isExistedById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TestResponse> fetchAll() {
		List<TestEntity> test = testRepository.findAllByEnableTrue();

		return test.stream().map(c -> mappingTestToResponse(c)).collect(Collectors.toList());
	}

	private TestResponse mappingTestToResponse(TestEntity test) {
		try {

			TestTypeEntity testType = testTypeService.findById(test.getTestType());

			if (ObjectUtils.anyNull(testType)) {
				throw new ValidateException("Can not found TestType Id");
			}
			return TestResponse.builder().build().convertFromEntity(test, testType);
		} catch (Exception e) {
			throw new GlobalException("Dữ liệu không hợp lệ! Vui lòng thử lại.");
		}
	}

	@Override
	public TestResponse fetchById(String id) {
		try {
			TestEntity entity = testRepository.findById(id).orElse(null);

			return mappingTestToResponse(entity);
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	@Override
	public TestResponse saveUsingDTO(TestRequest inputDto) {
		try {
			TestEntity entity = testRepository.save(inputDto.convertToEntity());

			return mappingTestToResponse(entity);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public TestResponse updateUsingDTO(TestRequest inputDto) {
		try {
			TestEntity entity = testRepository
					.save(inputDto.convertToEntity(testRepository.findById(inputDto.getId()).orElse(null)));

			return new TestResponse().convertFromEntity(entity);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ObjectResponsePaging<TestResponse> findAll(int page, int size) {
		Page<TestEntity> pages = testRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<TestResponse> elements = pages.getContent().stream().map(c -> mappingTestToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<TestResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public ObjectResponsePaging<TestResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TestResponse> fetchAllByTestTypeId(String testTypeId) {
		if(testTypeService.findById(testTypeId) == null) {
			throw new ValidateException("Not foung Test Type Id");
		}
		List<TestEntity> rvs = testRepository.findAllByEnableTrueAndTestType(testTypeId);
		return rvs.stream().map(c -> mappingTestToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public ObjectResponsePaging<TestResponse> findAllByTestTypeId(int page, int size, String testTypeId) {
		Page<TestEntity> pages = null;
		PageRequest pageRequest = PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate");
		
		// if not findAll with testTypeId
		if(!StringUtils.isNotBlank(testTypeId)) {
			pages = testRepository.findAllByEnableTrue(pageRequest);
			
		} else {
			
			if(testTypeService.findById(testTypeId) == null) {
				throw new ValidateException("Not foung Test Type Id");
			}
			
			pages = testRepository.findAllByEnableTrueAndTestType(pageRequest, testTypeId);
		}
		
		List<TestResponse> elements = pages.getContent().stream().map(c -> mappingTestToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<TestResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

}
