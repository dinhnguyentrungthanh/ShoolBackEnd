package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.util.List;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.ClassEntity;
import com.project.smartschool.entities.MajorEntity;

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
public class BlockResponse extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "Id " , name = "id",required = true, value = "id")
	private String id;
	
	@ApiModelProperty(notes = "Url " , name = "Url",required = true, value = "Url")
	private String url;
	
	@ApiModelProperty(notes = "Blockname " , name = "blockname",required = true, value = "blockname")
	private String blockname;

	@ApiModelProperty(notes = "Major " , name = "major",required = true, value = "major")
	private List<MajorEntity> majors;
	
	@ApiModelProperty(notes = "Classes " , name = "classes",required = true, value = "classes")
	private List<ClassEntity> classes;
	
	public BlockResponse convertFromEntity(BlockEntity entity, List<MajorEntity> majorentity, List<ClassEntity> classes) {
		mappingFields(entity);
		this.majors = majorentity;	
		this.classes = classes;
		return this;		
	}
	
	public BlockResponse convertFromEntity(BlockEntity entity) {
		mappingFields(entity);	
		return this;		
	}
	private void mappingFields(BlockEntity entity) {
		this.id = entity.getId();
		this.blockname = entity.getBlockname();
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.version = entity.getVersion();
		this.url = entity.getUrl();
	}

}
