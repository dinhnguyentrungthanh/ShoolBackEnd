package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.util.List;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.MathDesignEntity;

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
public class MathDesignResponse extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Id " , name = "id",required = true, value = "id")
	private String id;
	
	@ApiModelProperty(notes = "Url " , name = "url",required = true, value = "url")
	private String url;
	
	@ApiModelProperty(notes = "MathsDesignName " , name = "mathsDesignName",required = true, value = "mathsDesignName")
	private String mathDesignName;
	
	@ApiModelProperty(notes = "Major " , name = "Major",required = true, value = "Major")
	private String major;

	@ApiModelProperty(notes = "Chapter " , name = "major",required = true, value = "major")
	private List<ChapterEntity> chapters;
	
	public MathDesignResponse convertFromEntity(MathDesignEntity entity, List<ChapterEntity> chapterEntities) {
		mappingFields(entity);
		this.chapters = chapterEntities;	
		return this;		
	}
	public MathDesignResponse convertFromEntity(MathDesignEntity entity) {
		mappingFields(entity);	
		return this;		
	}
	private void mappingFields(MathDesignEntity entity) {
		this.id = entity.getId();
		this.mathDesignName = entity.getMathDesignName();
		this.major = entity.getMajor();
		
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.version = entity.getVersion();
		this.url = entity.getUrl();
	}
}
