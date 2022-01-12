package com.project.smartschool.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.project.smartschool.dto.request.ClassRequest;
import com.project.smartschool.dto.response.ClassResponse;
import com.project.smartschool.entities.ClassEntity;

public interface ClassService extends IBaseService<ClassEntity, String>,
		IDtoService<ClassResponse, String, ClassRequest>, IPageService<ClassResponse> {

	boolean isExistedByClassname(String classname);
	
	List<ClassEntity> findAllByIds(Set<String> ids);
	
	Map<String, Boolean> deleteUsersFromClassByIds(String classId, List<String> ids);
}
