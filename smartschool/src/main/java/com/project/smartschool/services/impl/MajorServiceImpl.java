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

import com.project.smartschool.dto.request.MajorRequest;
import com.project.smartschool.dto.response.MajorResponse;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.MajorEntity;
import com.project.smartschool.entities.MathDesignEntity;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.MajorRepository;
import com.project.smartschool.services.BlockService;
import com.project.smartschool.services.MajorService;
import com.project.smartschool.services.MathDesignService;
import com.project.smartschool.util.SeoUtils;

@Service
public class MajorServiceImpl implements MajorService{
	
	@Autowired
	private MajorService majorService;
	
	@Autowired MathDesignService mathDesignService;
	
	@Autowired
	private MajorRepository majorRepository;
	
	@Autowired
	private BlockService blockService;

	@Override
	public List<MajorEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MajorEntity findById(String id) {
		// TODO Auto-generated method stub
		return majorRepository.findById(id).orElse(null);
	}

	@Override
	public MajorEntity save(MajorEntity entity) {
		return majorRepository.save(entity);
	}

	@Override
	public List<MajorEntity> saveAll(List<MajorEntity> entities) {
		return majorRepository.saveAll(entities);
	}

	@Override
	public MajorEntity update(MajorEntity entity) {
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		MajorEntity entity = verify(id);
		entity.setEnable(false);
		majorRepository.save(entity);
		mathDesignService.deleteByIds(entity.getMathDesigns().stream().collect(Collectors.toList()));
		return true;
	}

	@Override
	public Map<String, Boolean> deleteByIds(List<String> ids) {
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for(String id: ids) {
			try {
				List<MajorEntity> majors = majorService.findAllByIds(ids);
				Set<String> mdsId = new HashSet<String>();
				majors.stream().forEach(md ->mdsId.addAll(md.getMathDesigns()));
				this.deleteById(id);
				mathDesignService.deleteByIds(mdsId.stream().collect(Collectors.toList()));
				m.put(id, true);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				m.put(id, false);
			}
		}
		return m;
	}

	@Override
	public boolean isExistedById(String id) {
		return majorRepository.existsById(id);
	}

	@Override
	public List<MajorResponse> fetchAll() {
		List<MajorEntity> majors = majorRepository.findAllByEnableTrue();
		return majors.stream().map(c -> mappingMajorToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public MajorResponse fetchById(String id) {
		MajorEntity majorEntity = majorRepository.findById(id).orElse(null);
		
		if(ObjectUtils.anyNull(majorEntity)) 
			throw new ValidateException("Không tìm thấy Môn");
		
		return mappingMajorToResponse(majorEntity);
	}

	@Override
	public MajorResponse saveUsingDTO(MajorRequest inputDto) {
		List<MajorEntity>  majorEntities = majorRepository.findAllByEnableFalse();
		for (MajorEntity majorEntity : majorEntities) {
			if(majorService.isExistedByMajorname(inputDto.getMajorname()) && majorEntity.getMajorname().equals(inputDto.getMajorname()))
				throw new ValidateException("Tên Môn này đã tồn tại");
		}
		return saveOrUpdateUsingDTO(inputDto, inputDto.convertToEntity());
	}

	@Override
	public MajorResponse updateUsingDTO(MajorRequest inputDto) {
		MajorEntity mj = verify(inputDto.getId());
		
		MajorEntity mjAll = majorRepository.findByMajorname(inputDto.getMajorname());

		if(ObjectUtils.allNotNull(mj) && ObjectUtils.allNotNull(mjAll)) {
			if(!inputDto.getId().equals(mjAll.getId()) && inputDto.getMajorname().trim().equals(mjAll.getMajorname())) {
				throw new ValidateException("Tên Môn này đã tồn tại");
			}
			
			if(inputDto.getId().equals(mjAll.getId()) && !inputDto.getMajorname().trim().equals(mjAll.getMajorname())) {
				throw new ValidateException("Tên Môn này đã tồn tại");
			}
		} 
		
		boolean hasAnyMajorNotExisted = inputDto.getMathDesigns().stream().anyMatch(e -> !mathDesignService.isExistedById(e));		
		if(hasAnyMajorNotExisted) 
			throw new ValidateException("Danh sách môn không hợp lệ");
		
		return saveOrUpdateUsingDTO(inputDto, majorService.findById(inputDto.getId()));
	}

	@Override
	public ObjectResponsePaging<MajorResponse> findAll(int page, int size) {
		Page<MajorEntity> pages = this.majorRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<MajorResponse> elements = pages.getContent().stream().map(c -> mappingMajorToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<MajorResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public ObjectResponsePaging<MajorResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MajorEntity> findAllByIds(Set<String> ids) {
		return StreamSupport.stream(majorRepository.findAllById(ids).spliterator(), false).filter(m -> m.isEnable())
				.collect(Collectors.toList());
	}

	@Override
	public boolean isExistedByMajorname(String majorname) {
		return !ObjectUtils.anyNull(majorRepository.findByMajorname(majorname));
	}
	
	private MajorResponse mappingMajorToResponse(MajorEntity majorEntity) {
		List<MathDesignEntity> mathDesignEntities = mathDesignService.findAllByIds(majorEntity.getMathDesigns()).stream().filter(m -> m.isEnable())
				.collect(Collectors.toList());		
		return MajorResponse.builder().build().convertFromEntity(majorEntity, mathDesignEntities);
	}
	
	private MajorResponse saveOrUpdateUsingDTO(MajorRequest inputDto, MajorEntity majorEntity) {
		MajorEntity temp = inputDto.convertToEntity(majorEntity);
		String url = SeoUtils.toFriendlyUrl(temp.getMajorname());
		if (inputDto.getId() == null) {
			if (majorRepository.existsByUrl(url)) {
				url = SeoUtils.toFriendlyUrlGeneric(temp.getMajorname());
			}
		}else {
			MajorEntity tempUpdate = majorRepository.findByUrl(temp.getUrl());
			if(tempUpdate != null && !tempUpdate.getUrl().equals(url)) {
				url = SeoUtils.toFriendlyUrlGeneric(temp.getMajorname());
			}
		}
		temp.setUrl(url);
		MajorEntity mj = majorService.save(temp);
		
		List<MathDesignEntity> mathDesignUpdate = inputDto.getMathDesigns().stream().map(e -> mappingResquestToEntity(e, majorEntity)).collect(Collectors.toList());
		
		// Update Block
		BlockEntity block = blockService.findById(inputDto.getBlock());
		Set<String> blocksUpdated = block.getMajors();
		blocksUpdated.add(mj.getId());		
		block.setMajors(blocksUpdated);		
				blockService.save(block);
		return MajorResponse.builder().build().convertFromEntity(mj, mathDesignUpdate);
	}
	
	private MathDesignEntity mappingResquestToEntity(String mathDesignId, MajorEntity mj) {
		MathDesignEntity mathDesignEntity = mathDesignService.findById(mathDesignId);		
		mathDesignEntity.setMajor(mj.getId());
		return mathDesignEntity;
	}
	
	private MajorEntity verify(String id) {
		MajorEntity bls = majorRepository.findById(id).orElse(null);
		if (ObjectUtils.anyNull(bls)) {
			throw new ValidateException("Môn không tồn tại");
		}
		return bls;
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<MajorEntity> findAllByIds(List<String> ids) {
		return StreamSupport
				  .stream( majorRepository.findAllById(ids).spliterator(), false)
				  .collect(Collectors.toList());
	}

	@Override
	public MajorEntity findByUrl(String url) {
		return majorRepository.findByUrl(url);
	}

}
