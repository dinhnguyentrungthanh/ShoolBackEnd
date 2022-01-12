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

import com.project.smartschool.dto.request.ChapterRequest;
import com.project.smartschool.dto.response.ChapterResponse;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.KnowledgeEntity;
import com.project.smartschool.entities.MathDesignEntity;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.ChapterRepository;
import com.project.smartschool.services.BlockService;
import com.project.smartschool.services.ChapterService;
import com.project.smartschool.services.KnowledgeService;
import com.project.smartschool.services.MathDesignService;
import com.project.smartschool.util.SeoUtils;

@Service
public class ChapterServiceImpl implements ChapterService{
	
	@Autowired
	private ChapterRepository chapterRepository;
	
	@Autowired
	private ChapterService chapterService;
	
	@Autowired 
	private KnowledgeService knowledgeService;
	
	@Autowired
	private BlockService blockService;
	
	@Autowired 
	private MathDesignService mathDesignService;

	@Override
	public List<ChapterEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChapterEntity findById(String id) {
		return chapterRepository.findById(id).orElse(null);
	}

	@Override
	public ChapterEntity save(ChapterEntity entity) {
		return chapterRepository.save(entity);
	}

	@Override
	public List<ChapterEntity> saveAll(List<ChapterEntity> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChapterEntity update(ChapterEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(String id) {
		ChapterEntity entity = verify(id);
		entity.setEnable(false);
		chapterRepository.save(entity);
		knowledgeService.deleteByIds(entity.getKnowledges().stream().collect(Collectors.toList()));
		return true;
	}

	@Override
	public Map<String, Boolean> deleteByIds(List<String> ids) {
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		for(String id: ids) {
			try {
				List<ChapterEntity> chapters = chapterService.findAllByIds(ids);
				Set<String> kns = new HashSet<String>();
				chapters.stream().forEach(kn -> kns.addAll(kn.getKnowledges()));
				this.deleteById(id);
				knowledgeService.deleteByIds(kns.stream().collect(Collectors.toList()));
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
		return chapterRepository.existsById(id);
	}

	@Override
	public List<ChapterResponse> fetchAll() {
		List<ChapterEntity> chapterEntities = chapterRepository.findAllByEnableTrue();
		return chapterEntities.stream().map(c -> mappingChapterToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public ChapterResponse fetchById(String id) {
		ChapterEntity chapterEntity = chapterRepository.findById(id).orElse(null);
		
		if(ObjectUtils.anyNull(chapterEntity)) 
			throw new ValidateException("Không tìm thấy Chương");
		
		return mappingChapterToResponse(chapterEntity);
	}

	@Override
	public ChapterResponse saveUsingDTO(ChapterRequest inputDto) {
		return saveOrUpdateUsingDTO(inputDto, inputDto.convertToEntity());
	}

	@Override
	public ChapterResponse updateUsingDTO(ChapterRequest inputDto) {
		verify(inputDto.getId());
//		
//		ChapterEntity ctAll = chapterRepository.findById(inputDto.getId()).;
		
		/*
		 * if(ObjectUtils.allNotNull(ct) && ObjectUtils.allNotNull(ctAll)) {
		 * if(!inputDto.getId().equals(ctAll.getId()) &&
		 * inputDto.getChaptername().trim().equals(ctAll.getChaptername())) { throw new
		 * ValidateException("Tên Chương này đã tồn tại"); }
		 * 
		 * if(inputDto.getId().equals(ctAll.getId()) &&
		 * !inputDto.getChaptername().trim().equals(ctAll.getChaptername())) { throw new
		 * ValidateException("Tên Chương này đã tồn tại"); } }
		 */	
		
		boolean hasAnyMajorNotExisted = inputDto.getKnowledges().stream().anyMatch(e -> !knowledgeService.isExistedById(e));		
		if(hasAnyMajorNotExisted) 
			throw new ValidateException("Danh sách môn không hợp lệ");

		return saveOrUpdateUsingDTO(inputDto, chapterService.findById(inputDto.getId()));
	}

	@Override
	public ObjectResponsePaging<ChapterResponse> findAll(int page, int size) {
		Page<ChapterEntity> pages = this.chapterRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<ChapterResponse> elements = pages.getContent().stream().map(c -> mappingChapterToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<ChapterResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public ObjectResponsePaging<ChapterResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExistedByChaptername(String chapterName) {
		 return !ObjectUtils.anyNull(chapterRepository.findByChaptername(chapterName));
	}

	@Override
	public List<ChapterEntity> findAllByIds(Set<String> ids) {
		return StreamSupport.stream(chapterRepository.findAllById(ids).spliterator(), false).filter(e -> e.isEnable())
				.collect(Collectors.toList());
	}

	private ChapterResponse mappingChapterToResponse(ChapterEntity chapterEntity) {
		List<KnowledgeEntity> knowledgeEntities = knowledgeService.findAllByIds(chapterEntity.getKnowledges()).stream().filter(knowledge -> knowledge.isEnable()).collect(Collectors.toList());	
		BlockEntity blockEnitities = blockService.findById(chapterEntity.getBlock());
		return ChapterResponse.builder().build().convertFromEntity(chapterEntity, blockEnitities, knowledgeEntities);
	}
	
	private ChapterResponse saveOrUpdateUsingDTO(ChapterRequest inputDto, ChapterEntity chapterEntity) {
		ChapterEntity temp = inputDto.convertToEntity(chapterEntity);
		String url = SeoUtils.toFriendlyUrl(temp.getChaptername());
		if (inputDto.getId() == null) {
			if (chapterRepository.existsByUrl(url)) {
				url = SeoUtils.toFriendlyUrlGeneric(temp.getChaptername());
			}
		}else {
			ChapterEntity tempUpdate = chapterRepository.findByUrl(temp.getUrl());
			if(tempUpdate != null && !tempUpdate.getUrl().equals(url)) {
				url = SeoUtils.toFriendlyUrlGeneric(temp.getChaptername());
			}
		}
		temp.setUrl(url);
		ChapterEntity ct = chapterService.save(temp);
		
		
		List<KnowledgeEntity> knowLedgeUpdated = inputDto.getKnowledges().stream().map(e -> mappingResquestToEntity(e, ct)).collect(Collectors.toList());
		
		MathDesignEntity entity = mathDesignService.findById(inputDto.getMathDesign());
		Set<String> mathDSUpdates = entity.getChapters();
		mathDSUpdates.add(ct.getId());
		entity.setChapters(mathDSUpdates);
		mathDesignService.save(entity);
		return ChapterResponse.builder().build().convertFromEntity(ct,null, knowLedgeUpdated);
	}
	
	private KnowledgeEntity mappingResquestToEntity(String knowledgeid, ChapterEntity ct) {
		KnowledgeEntity k = knowledgeService.findById(knowledgeid);		
		k.setChapter(ct.getId());
		return k;
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private ChapterEntity verify(String id) {
		ChapterEntity ct = chapterRepository.findById(id).orElse(null);
		if (ObjectUtils.anyNull(ct)) {
			throw new ValidateException("Chương không tồn tại");
		}
		return ct;
	}

	@Override
	public List<ChapterEntity> findAllByIds(List<String> ids) {
		return StreamSupport.stream(chapterRepository.findAllById(ids).spliterator(), false).filter(c -> c.isEnable())
				.collect(Collectors.toList());
	}

	@Override
	public ChapterEntity findByUrl(String url) {
		return chapterRepository.findByUrl(url);
	}
}
