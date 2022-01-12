package com.project.smartschool.services;

import java.util.List;

import com.project.smartschool.dto.request.PointRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.PointResponse;
import com.project.smartschool.entities.PointEntity;
import com.project.smartschool.helpers.classes.TestResult;

public interface PointService extends 	IBaseService<PointEntity, String>,
										IDtoService<PointResponse, String, PointRequest>,
										IPageService<PointResponse> {

	void updateTestResultStream(String id, PointRequest request);
	
	ObjectResponsePaging<PointResponse> findAllByUser(int page, int size, String user);
	
	ObjectResponsePaging<PointResponse> findAllByType(int page, int size, String type);
	
	List<PointResponse> fetchAllByUser(String user);
	
	List<PointResponse> fetchAllByType(String type);
	
}
