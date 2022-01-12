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

import com.project.smartschool.dto.request.MathDesignRequest;
import com.project.smartschool.dto.response.MathDesignResponse;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.MajorEntity;
import com.project.smartschool.entities.MathDesignEntity;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.MathDesignRepository;
import com.project.smartschool.services.ChapterService;
import com.project.smartschool.services.MajorService;
import com.project.smartschool.services.MathDesignService;
import com.project.smartschool.util.SeoUtils;

@Service
public class MathDesignServiceImpl implements MathDesignService{
	
	@Autowired
	private MathDesignRepository mathDesignRepository;
	
	@Autowired
	private MathDesignService mathDesignService;
	
	@Autowired
	private ChapterService chapterService;
	
	@Autowired 
	private MajorService majorService;

	@Override
	public List<MathDesignEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MathDesignEntity findById(String id) {
		return mathDesignRepository.findById(id).orElse(null);
	}

	@Override
	public MathDesignEntity save(MathDesignEntity entity) {
		return mathDesignRepository.save(entity);
	}

	@Override
	public List<MathDesignEntity> saveAll(List<MathDesignEntity> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MathDesignEntity update(MathDesignEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		MathDesignEntity entity = verify(id);
		entity.setEnable(false);
		mathDesignService.save(entity);
		//update Enable chapters 
		chapterService.deleteByIds(entity.getChapters().stream().collect(Collectors.toList()));
		return true;
	}

	@Override
	public Map<String, Boolean> deleteByIds(List<String> ids) {
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for(String id: ids) {
			try {
				List<MathDesignEntity> mds = mathDesignService.findAllByIds(ids.stream().collect(Collectors.toSet()));
				Set<String> chapterIds = new HashSet<String>(); 
				mds.stream().forEach(md -> chapterIds.addAll(md.getChapters()));
				this.deleteById(id);
				chapterService.deleteByIds(chapterIds.stream().collect(Collectors.toList()));
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
		return mathDesignRepository.existsById(id);
	}

	@Override
	public List<MathDesignResponse> fetchAll() {
		List<MathDesignEntity>  mathDesignEntities = mathDesignRepository.findAllByEnableTrue();
		return mathDesignEntities.stream().map(c -> mappingMathDesignToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public MathDesignResponse fetchById(String id) {
		MathDesignEntity mathDesignEntity = mathDesignRepository.findById(id).orElse(null);
		
		if(ObjectUtils.anyNull(mathDesignEntity) || !mathDesignEntity.isEnable()) 
			throw new ValidateException("Không tìm thấy Dạng Toán");
		
		return mappingMathDesignToResponse(mathDesignEntity);
	}

	@Override
	public MathDesignResponse saveUsingDTO(MathDesignRequest inputDto) {
		List<MathDesignEntity> designEntities = mathDesignRepository.findAllByEnableTrue();
		for (MathDesignEntity mathDesignEntity : designEntities) {
			if(mathDesignService.isExistedByMathDesignName(inputDto.getMathDesignName()) && mathDesignEntity.getMathDesignName().equals(inputDto.getMathDesignName()))
				throw new ValidateException("Tên Dạng Toán này đã tồn tại");
		}
		
		return saveOrUpdateUsingDTO(inputDto, inputDto.convertToEntity());
	}

	@Override
	public MathDesignResponse updateUsingDTO(MathDesignRequest inputDto) {
		MathDesignEntity mtd = verify(inputDto.getId());
		
		MathDesignEntity mstAll = mathDesignRepository.findByMathDesignName(inputDto.getMathDesignName());
		
		if(ObjectUtils.allNotNull(mtd) && ObjectUtils.allNotNull(mstAll)) {
			if(!inputDto.getId().equals(mstAll.getId()) && inputDto.getMathDesignName().trim().equals(mstAll.getMathDesignName())) {
				throw new ValidateException("Tên Môn này đã tồn tại");
			}
			
			if(inputDto.getId().equals(mstAll.getId()) && !inputDto.getMathDesignName().trim().equals(mstAll.getMathDesignName())) {
				throw new ValidateException("Tên Môn này đã tồn tại");
			}
		} 			
		
		boolean hasAnyMajorNotExisted = inputDto.getChapters().stream().anyMatch(e -> !chapterService.isExistedById(e));		
		if(hasAnyMajorNotExisted) 
			throw new ValidateException("Danh sách Chương không hợp lệ");

		return saveOrUpdateUsingDTO(inputDto, mathDesignService.findById(inputDto.getId()));
	}

	@Override
	public ObjectResponsePaging<MathDesignResponse> findAll(int page, int size) {
		Page<MathDesignEntity> pages = this.mathDesignRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<MathDesignResponse> elements = pages.getContent().stream().map(c -> mappingMathDesignToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<MathDesignResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public ObjectResponsePaging<MathDesignResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExistedByMathDesignName(String mathDesignName) {
		return !ObjectUtils.anyNull(mathDesignRepository.findByMathDesignName(mathDesignName));
	}

	@Override
	public List<MathDesignEntity> findAllByIds(Set<String> ids) {
		return StreamSupport.stream(mathDesignRepository.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}

	private MathDesignResponse mappingMathDesignToResponse(MathDesignEntity mathDesignEntity) {
		List<ChapterEntity> chapterEntities = chapterService.findAllByIds(mathDesignEntity.getChapters()).stream().filter(c -> c.isEnable()).collect(Collectors.toList());		
		return MathDesignResponse.builder().build().convertFromEntity(mathDesignEntity, chapterEntities);
	}
	
	private MathDesignResponse saveOrUpdateUsingDTO(MathDesignRequest inputDto, MathDesignEntity mathDesignEntity) {
		
		// Create the MathDesign
		MathDesignEntity temp = inputDto.convertToEntity(mathDesignEntity);
		String url = SeoUtils.toFriendlyUrl(temp.getMathDesignName());
		if (inputDto.getId() == null) {
			if (mathDesignRepository.existsByUrl(url)) {
				url = SeoUtils.toFriendlyUrlGeneric(temp.getMathDesignName());
			}
		}else {
			MathDesignEntity tempUpdate = mathDesignRepository.findByUrl(temp.getUrl());
			if(tempUpdate != null && !tempUpdate.getUrl().equals(url)) {
				url = SeoUtils.toFriendlyUrlGeneric(temp.getMathDesignName());
			}
		}
		temp.setUrl(url);
		MathDesignEntity mtd = mathDesignService.save(temp);
		
		// Update Chapter
		List<ChapterEntity> chapterUpdates = inputDto.getChapters().stream().map(e -> mappingResquestToEntity(e, mtd)).collect(Collectors.toList());
		
		// Update Major
		MajorEntity  major = majorService.findById(inputDto.getMajor());
		Set<String> majorUpdates = major.getMathDesigns();
		majorUpdates.add(mtd.getId());
		major.setMathDesigns(majorUpdates);
		majorService.save(major);
		
		return MathDesignResponse.builder().build().convertFromEntity(mtd, chapterUpdates);
	}
	
	private ChapterEntity mappingResquestToEntity(String chapterId, MathDesignEntity mtd) {
		ChapterEntity c = chapterService.findById(chapterId);		
		c.setMathDesign(mtd.getId());
		return c;
	}
	
	private MathDesignEntity verify(String id) {
		MathDesignEntity mtd = mathDesignRepository.findById(id).orElse(null);
		if (ObjectUtils.anyNull(mtd)) {
			throw new ValidateException("Dạng Toán không tồn tại");
		}
		return mtd;
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<MathDesignEntity> findAllByIds(List<String> ids) {
		return StreamSupport.stream(mathDesignRepository.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public MathDesignEntity findByUrl(String url) {
		return mathDesignRepository.findByUrl(url);
	}
}
