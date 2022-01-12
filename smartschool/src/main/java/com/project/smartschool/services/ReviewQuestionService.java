package com.project.smartschool.services;

import java.util.List;

import com.project.smartschool.dto.request.ReviewQuestionRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.ReviewQuestionResponse;
import com.project.smartschool.entities.ReviewQuestionEntity;

public interface ReviewQuestionService extends 	IBaseService<ReviewQuestionEntity, String>,
												IDtoService<ReviewQuestionResponse, String, ReviewQuestionRequest>,
												IPageService<ReviewQuestionEntity> {

	List<ReviewQuestionResponse> fetchAllByKnowledgeId(String knowledgeId);
	
	ObjectResponsePaging<ReviewQuestionResponse> findAllByKnowledgeId(int currentPage, int size, String knowledgeId);
	
}
