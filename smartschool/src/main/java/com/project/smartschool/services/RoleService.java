package com.project.smartschool.services;

import com.project.smartschool.dto.request.RoleRequest;
import com.project.smartschool.dto.response.RoleResponse;
import com.project.smartschool.entities.RoleEntity;

public interface RoleService extends IBaseService<RoleEntity, String>,
IDtoService<RoleResponse, String, RoleRequest>, IPageService<RoleResponse>{

}
