package com.project.smartschool.services;

import java.util.List;

import com.project.smartschool.dto.request.TestTypeRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.TestTypeResponse;
import com.project.smartschool.entities.TestTypeEntity;

public interface TestTypeService extends 	IBaseService<TestTypeEntity, String>,
											IDtoService<TestTypeResponse, String, TestTypeRequest>,
											IPageService<TestTypeResponse>  {
	
	List<TestTypeResponse> fetchAllByBlockSeo(String blockId);
	
	ObjectResponsePaging<TestTypeResponse> findAllByBlockSeo(int page, int size, String blockId);
}
