package com.project.smartschool.services;

import java.util.List;

import com.project.smartschool.dto.request.TestRequest;
import com.project.smartschool.dto.response.ObjectResponsePaging;
import com.project.smartschool.dto.response.TestResponse;
import com.project.smartschool.entities.TestEntity;

public interface TestService extends IBaseService<TestEntity, String>, IDtoService<TestResponse, String, TestRequest>,
		IPageService<TestResponse> {

	public List<TestResponse> fetchAllByTestTypeId(String testTypeId);

	public ObjectResponsePaging<TestResponse> findAllByTestTypeId(int currentPage, int size, String testTypeId);

}
