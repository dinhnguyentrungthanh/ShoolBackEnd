package com.project.smartschool.dto.request;

import java.util.HashSet;
import java.util.Set;

import com.project.smartschool.entities.RoleGroupEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RoleGroupRequest {

	private String id;
	
	private String name;

	private Set<String> roles =new HashSet<String>();
	
	public RoleGroupEntity convertToEntity() {
		return mappingFields(new RoleGroupEntity());
	}

	public RoleGroupEntity convertToEntity(RoleGroupEntity entity) {
		return mappingFields(entity);
	}

	private RoleGroupEntity mappingFields(RoleGroupEntity entity) {
		entity.setId(this.id);
		entity.setName(this.name);
		entity.setRoles(this.roles);
		return entity;
	}
}
