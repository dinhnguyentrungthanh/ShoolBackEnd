package com.project.smartschool.dto.request;

import com.project.smartschool.entities.RoleEntity;
import com.project.smartschool.enums.ERole;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RoleRequest {

	private String id;
	
	private ERole name;
	
	private Integer code;
	
	public RoleEntity convertToEntity() {
		return mappingFields(new RoleEntity());
	}

	public RoleEntity convertToEntity(RoleEntity entity) {
		return mappingFields(entity);
	}

	private RoleEntity mappingFields(RoleEntity entity) {
		entity.setId(this.id);
		entity.setName(this.name);
		entity.setCode(this.code);
		return entity;
	}

}
