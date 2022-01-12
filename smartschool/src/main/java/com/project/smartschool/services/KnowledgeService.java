package com.project.smartschool.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.project.smartschool.dto.request.ChapterOfKnowledgeRequest;
import com.project.smartschool.dto.request.KnowledgeRequest;
import com.project.smartschool.dto.response.KnowledgeResponse;
import com.project.smartschool.entities.KnowledgeEntity;

public interface KnowledgeService extends 	IBaseService<KnowledgeEntity, String>,
											IDtoService<KnowledgeResponse, String, KnowledgeRequest>,
											IPageService<KnowledgeResponse> {

	Map<String, Object> findAllWithPage(String key, String status, String chapterId, Integer page, Integer size);

	List<KnowledgeEntity> findAllByIds(Set<String> knowledges);
	
	List<KnowledgeEntity> searchingAll(String keywords);
	
	KnowledgeResponse updateChapter(String kwId, ChapterOfKnowledgeRequest data);

	KnowledgeResponse saveChapter(ChapterOfKnowledgeRequest data);
	
	KnowledgeEntity findByUrl(String url);
		
}
