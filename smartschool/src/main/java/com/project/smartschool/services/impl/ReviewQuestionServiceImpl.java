package com.project.smartschool.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.project.smartschool.dto.request.ReviewQuestionRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.ReviewQuestionResponse;
import com.project.smartschool.entities.KnowledgeEntity;
import com.project.smartschool.entities.ReviewQuestionEntity;
import com.project.smartschool.errors.GlobalException;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.ReviewQuestionRepository;
import com.project.smartschool.services.KnowledgeService;
import com.project.smartschool.services.ReviewQuestionService;

@Service
public class ReviewQuestionServiceImpl implements ReviewQuestionService {

	@Autowired
	private ReviewQuestionRepository reviewQuestionRepository;
	
	@Autowired
	private KnowledgeService knowledgeService;
	
	@Override
	public List<ReviewQuestionEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReviewQuestionEntity findById(String id) {
		return reviewQuestionRepository.findById(id).orElse(null);
	}

	@Override
	public ReviewQuestionEntity save(ReviewQuestionEntity entity) {
		return reviewQuestionRepository.save(entity);
	}

	@Override
	public List<ReviewQuestionEntity> saveAll(List<ReviewQuestionEntity> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReviewQuestionEntity update(ReviewQuestionEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public boolean deleteById(String id) {
		ReviewQuestionEntity rv = reviewQuestionRepository.findById(id).orElse(null);
		
		if(ObjectUtils.anyNull(rv)) {
			throw new ValidateException("Can not found Review Question ID");
		}
		
		rv.setEnable(false);		
		
		return reviewQuestionRepository.save(rv) != null;
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
	public List<ReviewQuestionResponse> fetchAll() {
		return null;
	}

	@Override
	public ReviewQuestionResponse fetchById(String id) {
			return null;
	}

	@Transactional
	@Override
	public ReviewQuestionResponse saveUsingDTO(ReviewQuestionRequest inputDto) {
		try {			
			ReviewQuestionEntity entity = reviewQuestionRepository.save(inputDto.convertToEntity());
			
			// update knowledge
			KnowledgeEntity kn = knowledgeService.findById(entity.getKnowledge());
			
			if(ObjectUtils.anyNull(kn)) {
				throw new ValidateException("Can not found Knowledge Id");
			}
					
			Set<String> rqs = kn.getReviewQuestions();
			rqs.add(entity.getId());
			kn.setReviewQuestions(rqs);			
			knowledgeService.save(kn);
			
			return mappingReviewQuestionToResponse(entity);
		} catch (Exception e) {
			throw new GlobalException(e.getLocalizedMessage());
		}
	}

	@Transactional
	@Override
	public ReviewQuestionResponse updateUsingDTO(ReviewQuestionRequest inputDto) {	
			
			if(StringUtils.isBlank(inputDto.getId())) {
				throw new ValidateException("Can not found Review Question Id");
			}
			
			ReviewQuestionEntity rw = inputDto.convertToEntity(findById(inputDto.getId()));
			
			ReviewQuestionEntity entity = reviewQuestionRepository.save(rw);	
			
			// update knowledge
			KnowledgeEntity kn = knowledgeService.findById(entity.getKnowledge());
			
			if(ObjectUtils.anyNull(kn)) {
				throw new ValidateException("Can not found Knowledge Id");
			}
			
			Set<String> rqs = kn.getReviewQuestions();
			rqs.add(entity.getId());
			kn.setReviewQuestions(rqs);			
			knowledgeService.save(kn);
			
			return mappingReviewQuestionToResponse(entity);
	}
	

	@Override
	public ObjectResponsePaging<ReviewQuestionEntity> findAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectResponsePaging<ReviewQuestionEntity> findAll(String testId, String parrentId, Integer page,
			Integer size) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ReviewQuestionEntity> findAllByIds(List<String> ids) {
		return StreamSupport.stream(reviewQuestionRepository.findAllById(ids).spliterator(), false)
				.collect(Collectors.toList());
	}
	
	private ReviewQuestionResponse mappingReviewQuestionToResponse(ReviewQuestionEntity entity) {
		try {
			return ReviewQuestionResponse.builder().build().convertFromEntity(entity);
		} catch (Exception e) {
			throw new GlobalException("Dữ liệu không hợp lệ!");
		}
	}

	@Override
	public List<ReviewQuestionResponse> fetchAllByKnowledgeId(String knowledgeId) {
		if(knowledgeService.findById(knowledgeId) == null) {
			throw new ValidateException("Not foung Knowledge Id");
		}
		List<ReviewQuestionEntity> rvs = reviewQuestionRepository.findAllByEnableTrueAndKnowledge(knowledgeId);
		return rvs.stream().map(c -> mappingReviewQuestionToResponse(c)).collect(Collectors.toList());
	}

	@Override
	public ObjectResponsePaging<ReviewQuestionResponse> findAllByKnowledgeId(int page, int size,
			String knowledgeId) {
		Page<ReviewQuestionEntity> pages = null;
		PageRequest pageRequest = PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate");
		
		// if not findAll with knowledgeId
		if(!StringUtils.isNotBlank(knowledgeId)) {
			pages = this.reviewQuestionRepository.findAllByEnableTrue(pageRequest);
			
		} else {
			if(knowledgeService.findById(knowledgeId) == null) {
				throw new ValidateException("Not foung Knowledge Id");
			}
			pages = this.reviewQuestionRepository.findAllByEnableTrueAndKnowledge(pageRequest, knowledgeId);
		}
		
		List<ReviewQuestionResponse> elements = pages.getContent().stream().map(c -> mappingReviewQuestionToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<ReviewQuestionResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}	

}
