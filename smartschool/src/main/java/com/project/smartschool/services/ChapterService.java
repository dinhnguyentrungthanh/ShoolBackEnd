package com.project.smartschool.services;

import java.util.List;
import java.util.Set;

import com.project.smartschool.dto.request.ChapterRequest;
import com.project.smartschool.dto.response.ChapterResponse;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.MajorEntity;

public interface ChapterService extends IBaseService<ChapterEntity, String>,
												IDtoService<ChapterResponse, String, ChapterRequest>, IPageService<ChapterResponse>{
	boolean isExistedByChaptername(String chapterName);
	
	List<ChapterEntity> findAllByIds(Set<String> ids);
	
	ChapterEntity findByUrl(String url);

}
