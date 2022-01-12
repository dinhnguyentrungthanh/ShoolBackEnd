package com.project.smartschool.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.smartschool.dto.request.TestTypeRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.TestTypeResponse;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.KnowledgeEntity;
import com.project.smartschool.entities.ReviewQuestionEntity;
import com.project.smartschool.entities.TestEntity;
import com.project.smartschool.entities.TestTypeEntity;
import com.project.smartschool.errors.GlobalException;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.TestTypeRepository;
import com.project.smartschool.services.BlockService;
import com.project.smartschool.services.TestService;
import com.project.smartschool.services.TestTypeService;

@Service
public class TestTypeServiceImpl implements TestTypeService {

	@Autowired
	private TestTypeRepository testTypeRepository;
	
	@Autowired
	private BlockService blockService;
	
	@Autowired
	private TestService testService;
	
	@Override
	public List<TestTypeEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TestTypeEntity> findAllByIds(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestTypeEntity findById(String id) {
		return testTypeRepository.findById(id).orElse(null);
	}

	@Override
	public TestTypeEntity save(TestTypeEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TestTypeEntity> saveAll(List<TestTypeEntity> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestTypeEntity update(TestTypeEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public boolean deleteById(String id) {
		TestTypeEntity rv = testTypeRepository.findById(id).orElse(null);
		
		if(ObjectUtils.anyNull(rv)) {
			throw new ValidateException("Can not found Test Type ID");
		}
		
		rv.setEnable(false);	
		
		// test
		rv.getTests().stream().forEach(t -> {
			TestEntity test = testService.findById(t);
			test.setEnable(false);
			testService.save(test);
		});		
		
		return testTypeRepository.save(rv) != null;
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
	public List<TestTypeResponse> fetchAll() {
		List<TestTypeEntity> testType = testTypeRepository.findAllByEnableTrue();
		
		return testType.stream().map(c -> mappingTestTypeToResponse(c)).collect(Collectors.toList());
	}
	
	private TestTypeResponse mappingTestTypeToResponse(TestTypeEntity testType) {
		try {
			
			BlockEntity block = blockService.findById(testType.getBlock());
			
			if(ObjectUtils.anyNull(block) | !block.isEnable()) {
				throw new ValidateException("Can not found Block Id");
			}
			return TestTypeResponse.builder().build().convertFromEntity(testType, block);
		} catch (Exception e) {
			throw new GlobalException("Dữ liệu không hợp lệ! Vui lòng thử lại.");
		}
	}

	@Override
	public TestTypeResponse fetchById(String id) {
		return mappingTestTypeToResponse(verify(id));
	}

	@Override
	public TestTypeResponse saveUsingDTO(TestTypeRequest inputDto) {
		try {			
			
			// update block
			BlockEntity block = blockService.findById(inputDto.getBlock());
			
			if(ObjectUtils.anyNull(block) | !block.isEnable()) {
				throw new ValidateException("Can not found Block Id");
			}

			TestTypeEntity entity = testTypeRepository.save(inputDto.convertToEntity());
			
			return mappingTestTypeToResponse(entity);
		} catch (Exception e) {
			throw new GlobalException(e.getLocalizedMessage());
		}
	}

	@Override
	public TestTypeResponse updateUsingDTO(TestTypeRequest inputDto) {
try {			
			
			// update block
			BlockEntity block = blockService.findById(inputDto.getBlock());
			
			if(ObjectUtils.anyNull(block) | !block.isEnable()) {
				throw new ValidateException("Can not found Block");
			}
			
			TestTypeEntity entity = findById(inputDto.getId());
			
			if(ObjectUtils.anyNull(entity) | !entity.isEnable()) {
				throw new ValidateException("Can not found Test Type");
			}

			TestTypeEntity entityUpdated = testTypeRepository.save(inputDto.convertToEntity(entity));
			
			return mappingTestTypeToResponse(entityUpdated);
		} catch (Exception e) {
			throw new GlobalException(e.getLocalizedMessage());
		}
	}

	@Override
	public ObjectResponsePaging<TestTypeResponse> findAll(int page, int size) {
		Page<TestTypeEntity> pages = testTypeRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<TestTypeResponse> elements = pages.getContent().stream().map(c -> mappingTestTypeToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<TestTypeResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public ObjectResponsePaging<TestTypeResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TestTypeResponse> fetchAllByBlockSeo(String blockSeo) {
		
		BlockEntity block = blockService.findByUrl(blockSeo);
		
		List<TestTypeEntity> testTypes = testTypeRepository.findAllByEnableTrueAndBlock(block.getId());		
		return testTypes.stream().map(c -> mappingTestTypeToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public ObjectResponsePaging<TestTypeResponse> findAllByBlockSeo(int page, int size, String blockSeo) {
		BlockEntity block = blockService.findByUrl(blockSeo);
		PageRequest pageRequest = PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate");
		Page<TestTypeEntity> pages = testTypeRepository.findAllByEnableTrueAndBlock(pageRequest, block.getId());
		List<TestTypeResponse> elements = pages.getContent().stream().map(c -> mappingTestTypeToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<TestTypeResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}
	
	private TestTypeEntity verify(String testTypeId) {
		TestTypeEntity testType = findById(testTypeId);
		if(ObjectUtils.anyNull(testType) || !testType.isEnable()) {
			throw new ValidateException("Not found TestType Id");
		}
		
		return testType;
	}


}
