package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.util.List;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.MathDesignEntity;
import com.project.smartschool.entities.RoleEntity;
import com.project.smartschool.entities.RoleGroupEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleGroupResponse extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "Id " , name = "id",required = true, value = "id")
	private String id;
	
	@ApiModelProperty(notes = "Name " , name = "Name",required = true, value = "Name")
	private String name;
	
	@ApiModelProperty(notes = "roles " , name = "Roles",required = true, value = "Roles")
	private List<RoleEntity> roles;
	
	public RoleGroupResponse convertFromEntity(RoleGroupEntity entity, List<RoleEntity> roles) {
		mappingFields(entity);
		this.roles = roles;	
		return this;		
	}
	public RoleGroupResponse convertFromEntity(RoleGroupEntity entity) {
		mappingFields(entity);	
		return this;		
	}
	private void mappingFields(RoleGroupEntity entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.version = entity.getVersion();
	}
}
