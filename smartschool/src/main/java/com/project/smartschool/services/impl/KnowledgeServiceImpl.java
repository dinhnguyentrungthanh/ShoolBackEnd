package com.project.smartschool.services.impl;

import java.util.HashMap;
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

import com.project.smartschool.dto.request.ChapterOfKnowledgeRequest;
import com.project.smartschool.dto.request.KnowledgeRequest;
import com.project.smartschool.dto.response.KnowledgeResponse;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.ReviewQuestionResponse;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.KnowledgeEntity;
import com.project.smartschool.entities.MajorEntity;
import com.project.smartschool.entities.MathDesignEntity;
import com.project.smartschool.entities.ReviewQuestionEntity;
import com.project.smartschool.errors.GlobalException;
import com.project.smartschool.errors.ValidateException;
import com.project.smartschool.repository.KnowledgeRepository;
import com.project.smartschool.services.ChapterService;
import com.project.smartschool.services.KnowledgeService;
import com.project.smartschool.services.MajorService;
import com.project.smartschool.services.MathDesignService;
import com.project.smartschool.services.ReviewQuestionService;
import com.project.smartschool.util.SeoUtils;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

	@Autowired
	private MathDesignService mathDesignService;
	
	@Autowired
	private ChapterService chapterService;
		
	@Autowired
	private MajorService majorService;
	
	@Autowired
	private ReviewQuestionService reviewQuestionService;

	@Autowired
	private KnowledgeRepository knowledgeRepository;
	
	
	@Override
	public List<KnowledgeEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KnowledgeEntity findById(String id) {
		return knowledgeRepository.findById(id).orElse(null);
	}

	@Override
	public KnowledgeEntity save(KnowledgeEntity entity) {
		return knowledgeRepository.save(entity);
	}

	@Override
	public List<KnowledgeEntity> saveAll(List<KnowledgeEntity> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KnowledgeEntity update(KnowledgeEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public boolean deleteById(String id) {
		KnowledgeEntity knowledgeEntity = verify(id);
		knowledgeEntity.setEnable(false);
		
		// disable all review question
		Set<String> reviewIds = knowledgeEntity.getReviewQuestions();
		reviewIds.stream().forEach(r -> {
			ReviewQuestionEntity rqe = reviewQuestionService.findById(r);			
			if(ObjectUtils.allNotNull(rqe)) {
				rqe.setEnable(false);
				reviewQuestionService.save(rqe);	
			}		
		});
		
		return knowledgeRepository.save(knowledgeEntity) != null;
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
		return knowledgeRepository.existsById(id);
	}

	@Override
	public List<KnowledgeResponse> fetchAll() {
		List<KnowledgeEntity> knows = knowledgeRepository.findAllByEnableTrue();
		
		return knows.stream().map(c -> mappingKnowledgeToResponse(c)).collect(Collectors.toList());
	}
	
	private KnowledgeResponse mappingKnowledgeToResponse(KnowledgeEntity knowledgeEntity) {
		
		try {
			// Chapter
			ChapterEntity chapter = chapterService.findById(knowledgeEntity.getChapter());
			
			// MathDesign
			String mathDesignId = chapter.getMathDesign();
			
			// Major
			MathDesignEntity md = mathDesignService.findById(mathDesignId);
			
			String majorId = md.getMajor();
			
			// Block 
			MajorEntity mj = majorService.findById(majorId);
			
			String blockId = mj.getBlock();
			
			// Knowledge
			List<String> klIds = knowledgeEntity.getReviewQuestions().stream().collect(Collectors.toList());
			Set<ReviewQuestionResponse> reviewQuestions = reviewQuestionService.findAllByIds(klIds)
					.stream().filter(r -> r.isEnable()).map(r -> ReviewQuestionResponse.builder().build().convertFromEntity(r)).collect(Collectors.toSet());

			return KnowledgeResponse.builder().build().convertFromEntity(knowledgeEntity, chapter, reviewQuestions).mappingDeepFileds(blockId, mathDesignId);
		} catch (Exception e) {
			throw new GlobalException("Dữ liệu không hợp lệ! Vui lòng thử lại.");
		}
		
	}

	@Override
	public KnowledgeResponse fetchById(String id) {
		KnowledgeEntity know = knowledgeRepository.findById(id).orElse(null);
		if(ObjectUtils.anyNull(know) || !know.isEnable())
			throw new GlobalException("Không tồn tại Kiến thức");
		return mappingKnowledgeToResponse(know);
	}

	@Override
	public KnowledgeResponse saveUsingDTO(KnowledgeRequest inputDto) {
		return null;
		
	}

	@Override
	public KnowledgeResponse updateUsingDTO(KnowledgeRequest inputDto) {
		return null;
	}

	@Override
	public Map<String, Object> findAllWithPage(String key, String status, String chapter, Integer page, Integer size) {
		return null;
	}

	@Override
	public ObjectResponsePaging<KnowledgeResponse> findAll(int page, int size) {
		Page<KnowledgeEntity> pages = this.knowledgeRepository.findAllByEnableTrue(PageRequest.of(page, size, Direction.DESC, "createdDate", "updatedDate"));
		List<KnowledgeResponse> elements = pages.getContent().stream().map(c -> mappingKnowledgeToResponse(c)).collect(Collectors.toList());
		
		return ObjectResponsePaging.<KnowledgeResponse>builder()
				.totalElements(pages.getTotalElements())
				.totalPages(pages.getTotalPages())
				.size(size)
				.currentPage(page)
				.elements(elements)
				.build();
	}

	@Override
	public ObjectResponsePaging<KnowledgeResponse> findAll(String testId, String parrentId, Integer page, Integer size)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KnowledgeEntity> findAllByIds(Set<String> knowledges) {
		return StreamSupport.stream(knowledgeRepository.findAllById(knowledges).spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public boolean deleteForceById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<KnowledgeEntity> findAllByIds(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KnowledgeEntity> searchingAll(String keywords) {
		return knowledgeRepository.searchingAll(keywords);
	}

	private KnowledgeEntity verify(String id) {
		KnowledgeEntity ct = knowledgeRepository.findById(id).orElse(null);
		if (ObjectUtils.anyNull(ct)) {
			throw new ValidateException("Kiến Thức không tồn tại");
		}
		return ct;
	}

	@Override
	public KnowledgeResponse updateChapter(String kwId, ChapterOfKnowledgeRequest data) {
		KnowledgeEntity know = knowledgeRepository.findById(kwId).orElse(null);
		if(ObjectUtils.anyNull(know) || !know.isEnable())
			throw new GlobalException("Không tồn tại Kiến thức");
		
		// update chapter
		know.setChapter(data.getChapter());
		
		know.setContents(data.getContents());
		
		// update url seo		
		String url = SeoUtils.toFriendlyUrl(data.getKnowledgeName());
		
		KnowledgeEntity k  = knowledgeRepository.findByUrl(url);
		if(ObjectUtils.allNotNull(k) && !k.getUrl().equals(know.getUrl())) {
			url = SeoUtils.toFriendlyUrlGeneric(data.getKnowledgeName());
		}			

		know.setKnowledgeName(data.getKnowledgeName());
		know.setUrl(url);
				
		// build response
		KnowledgeResponse knowResponse = mappingKnowledgeToResponse(know);
		
		// validate data from block -> mathDesign --> know
		boolean isValidChapter = knowResponse.getChapter().equals(data.getChapter());
		boolean isValidMathDesign = knowResponse.getMathDesign().equals(data.getMathDesign());
		boolean isValidBlock = knowResponse.getBlock().equals(data.getBlock());
		
		if(!isValidChapter || !isValidMathDesign || !isValidBlock) {
			throw new GlobalException("Dữ liệu không hợp lệ! Vui lòng thử lại.");
		}		
		
		// update knowledge
		knowledgeRepository.save(know);	
		
		// get chapter
		ChapterEntity chapter = chapterService.findById(data.getChapter());
		
		chapter.setBlock(data.getBlock());
		chapter.setMathDesign(data.getMathDesign());
		Set<String> kws = chapter.getKnowledges();
		kws.add(kwId);
		chapter.setKnowledges(kws);
		
		// update chapter
		chapterService.save(chapter);
		
		return knowResponse;
	}
	
	@Override
	public KnowledgeResponse saveChapter(ChapterOfKnowledgeRequest data) {
		KnowledgeEntity know = new KnowledgeEntity();
		
		// update chapter
		know.setChapter(data.getChapter());
		
		know.setContents(data.getContents());
		
		// create url seo		
		String url = SeoUtils.toFriendlyUrl(data.getKnowledgeName());
		
		// check the url seo
		KnowledgeEntity k  = knowledgeRepository.findByUrl(url);
		if(ObjectUtils.allNotNull(k) && !k.getUrl().equals(know.getUrl())) {
			url = SeoUtils.toFriendlyUrlGeneric(data.getKnowledgeName());
		}			

		know.setKnowledgeName(data.getKnowledgeName());
		know.setUrl(url);
				
		// build response
		KnowledgeResponse knowResponse = mappingKnowledgeToResponse(know);
		
		// validate data from block -> mathDesign --> know
		boolean isValidChapter = knowResponse.getChapter().equals(data.getChapter());
		boolean isValidMathDesign = knowResponse.getMathDesign().equals(data.getMathDesign());
		boolean isValidBlock = knowResponse.getBlock().equals(data.getBlock());
		
		if(!isValidChapter || !isValidMathDesign || !isValidBlock) {
			throw new GlobalException("Dữ liệu không hợp lệ! Vui lòng thử lại.");
		}		
		
		// save knowledge
		KnowledgeEntity knowCreated = knowledgeRepository.save(know);	
		
		// get chapter
		ChapterEntity chapter = chapterService.findById(data.getChapter());
		
		chapter.setBlock(data.getBlock());
		chapter.setMathDesign(data.getMathDesign());
		Set<String> kws = chapter.getKnowledges();
		kws.add(knowCreated.getId());
		chapter.setKnowledges(kws);
		
		// update chapter
		chapterService.save(chapter);
		
		return knowResponse;
	}

	@Override
	public KnowledgeEntity findByUrl(String url) {
		return knowledgeRepository.findByUrl(url);
	}
}
