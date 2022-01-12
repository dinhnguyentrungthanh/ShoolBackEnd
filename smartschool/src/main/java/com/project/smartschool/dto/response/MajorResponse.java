package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.util.List;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.MajorEntity;
import com.project.smartschool.entities.MathDesignEntity;

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
public class MajorResponse extends BaseEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Id ", name = "id", required = true, value = "id")
	private String id;

	@ApiModelProperty(notes = "Url ", name = "url", required = true, value = "url")
	private String url;
	
	@ApiModelProperty(notes = "MajorName ", name = "majorName", required = true, value = "majorName")
	private String majorname;

	@ApiModelProperty(notes = "Block ", name = "block", required = true, value = "block")
	private String block;

	@ApiModelProperty(notes = "MathDesign ", name = "mathsDesign", required = true, value = "mathsDesign")
	private List<MathDesignEntity> mathDesigns;

	public MajorResponse convertFromEntity(MajorEntity entity, List<MathDesignEntity> mathDesignEntity) {
		mappingFields(entity);
		this.mathDesigns = mathDesignEntity;
		return this;
	}

	private void mappingFields(MajorEntity entity) {
		this.id = entity.getId();
		this.majorname = entity.getMajorname();
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.block = entity.getBlock();
		this.version = entity.getVersion();
		this.url = entity.getUrl();
	}

}
