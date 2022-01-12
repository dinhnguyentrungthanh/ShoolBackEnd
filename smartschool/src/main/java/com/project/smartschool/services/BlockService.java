package com.project.smartschool.services;

import java.util.List;

import com.project.smartschool.dto.request.BlockRequest;
import com.project.smartschool.dto.response.BlockResponse;
import com.project.smartschool.entities.BlockEntity;

public interface BlockService extends IBaseService<BlockEntity, String> ,
										IDtoService<BlockResponse, String, BlockRequest>, IPageService<BlockResponse>{

	boolean isExistedByBlockname(String blockname);
	
	boolean deleteMajors(String blockId, List<String> majorIds);
	
	boolean deleteClasses(String blockId, List<String> classIds);
	
	BlockEntity findByUrl(String url);
}
