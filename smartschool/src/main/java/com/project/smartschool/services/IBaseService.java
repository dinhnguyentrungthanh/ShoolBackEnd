package com.project.smartschool.services;

import java.util.List;
import java.util.Map;

public interface IBaseService<T, ID> {
	
	/**
	 * 
	 * @return
	 */
	List<T> findAll();	
	
	List<T> findAllByIds(List<String> ids);
	
	T findById(ID id);
	
	T save(T entity);
	
	List<T> saveAll(List<T> entities);
		
	T update(T entity);
	
	boolean deleteById(ID id);
	
	boolean deleteForceById(ID id);
	
	Map<ID, Boolean> deleteByIds(List<ID> ids);
	
	boolean isExistedById(ID id);
}
