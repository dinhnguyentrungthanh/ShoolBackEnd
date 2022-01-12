package com.project.smartschool.dto.response;

import java.io.Serializable;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.RoleEntity;
import com.project.smartschool.enums.ERole;

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
public class RoleResponse extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Id " , name = "id",required = true, value = "id")
	private String id;
	
	@ApiModelProperty(notes = "Name " , name = "Name",required = true, value = "Name")
	private ERole name;
	
	@ApiModelProperty(notes = "Code " , name = "code",required = true, value = "code")
	private Integer code;
	
	public RoleResponse convertFromEntity(RoleEntity entity) {
		mappingFields(entity);
		return this;		
	}
	private void mappingFields(RoleEntity entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.version = entity.getVersion();
	}
}
