package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.util.Set;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.ClassEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassResponse extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "Id " , name = "id",required = true, value = "id")
	private String id;
	
	@ApiModelProperty(notes = "Classname " , name = "classname",required = true, value = "classname")
	private String classname;

	@ApiModelProperty(notes = "Blocks " , name = "block",required = true, value = "block")
	private String block;
	
//	@ApiModelProperty(notes = "Name " , name = "name",required = true, value = "name")
//	private List<UserEntity> users; 
	@ApiModelProperty(notes = "Name " , name = "name",required = true, value = "name")
	private Set<String> users; 
	
	public ClassResponse convertFromEntity(ClassEntity entity) {
		mappingFields(entity);
		return this;		
	}	
	
	public ClassResponse convertFromEntity(ClassEntity entity, Set<String> users) {
		mappingFields(entity);
		this.users = users;
		return this;		
	}
	
	private void mappingFields(ClassEntity entity) {
		this.id = entity.getId();
		this.classname = entity.getClassname();
		this.block = entity.getBlock();
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.version = entity.getVersion();
	}
	
}
