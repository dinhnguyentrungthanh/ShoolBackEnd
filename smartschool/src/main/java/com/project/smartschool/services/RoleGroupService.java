package com.project.smartschool.services;

import java.util.List;
import java.util.Set;

import com.project.smartschool.dto.request.RoleGroupRequest;
import com.project.smartschool.dto.response.RoleGroupResponse;
import com.project.smartschool.entities.RoleGroupEntity;

public interface RoleGroupService extends IBaseService<RoleGroupEntity, String>,
IDtoService<RoleGroupResponse, String, RoleGroupRequest>, IPageService<RoleGroupResponse>{
	
	boolean isExistedByName(String name);
	
	List<RoleGroupEntity> findAllByIds(Set<String> ids);

}
