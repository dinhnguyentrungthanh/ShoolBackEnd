package com.project.smartschool.services;

import java.util.List;

import com.project.smartschool.dto.request.KnowledgeFilterRequest;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.KnowledgeEntity;
import com.project.smartschool.entities.MathDesignEntity;

public interface SearchAndFilterService {
	
	List<MathDesignEntity> fetchAllMathDesignFromBlocks(List<String> blockIds);
	
	List<ChapterEntity> fetchAllChapterFromMathDesign(List<String> mdIds);
	
	List<KnowledgeEntity> searchKnowLedge(KnowledgeFilterRequest filter);
}
