package com.project.smartschool.services;

import java.util.List;
import java.util.Set;

import com.project.smartschool.dto.request.MathDesignRequest;
import com.project.smartschool.dto.response.MathDesignResponse;
import com.project.smartschool.entities.MajorEntity;
import com.project.smartschool.entities.MathDesignEntity;

public interface MathDesignService extends IBaseService<MathDesignEntity, String> ,
IDtoService<MathDesignResponse, String, MathDesignRequest>, IPageService<MathDesignResponse>{

	boolean isExistedByMathDesignName(String mathDesignName);
	
	List<MathDesignEntity> findAllByIds(Set<String> ids);
	
	MathDesignEntity findByUrl(String url);
}
