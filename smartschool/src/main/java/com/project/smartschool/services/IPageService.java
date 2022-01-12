package com.project.smartschool.services;

import com.project.smartschool.dto.response.ObjectResponsePaging;

public interface IPageService<T> {
		
	/**
	 * Lấy tất cả Entity sử dụng page
	 * @param page
	 * @param size
	 * @return
	 */
	ObjectResponsePaging<T> findAll(int page, int size);
	
	ObjectResponsePaging<T> findAll(String testId, String parrentId, Integer page, Integer size) throws Exception;
}
