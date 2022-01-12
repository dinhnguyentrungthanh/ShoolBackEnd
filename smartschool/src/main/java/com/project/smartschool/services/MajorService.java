package com.project.smartschool.services;

import java.util.List;
import java.util.Set;

import com.project.smartschool.dto.request.MajorRequest;
import com.project.smartschool.dto.response.MajorResponse;
import com.project.smartschool.entities.MajorEntity;

public interface MajorService extends IBaseService<MajorEntity, String>,
IDtoService<MajorResponse, String, MajorRequest>, IPageService<MajorResponse>{

	List<MajorEntity> findAllByIds(Set<String> ids);
	
	boolean isExistedByMajorname(String majorname);
	
	MajorEntity findByUrl(String url);
}
