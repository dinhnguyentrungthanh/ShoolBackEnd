package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.util.List;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.KnowledgeEntity;

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
public class ChapterResponse extends BaseEntity implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Id " , name = "id",required = true, value = "id")
	private String id;
	
	@ApiModelProperty(notes = "Url " , name = "url",required = true, value = "url")
	private String url;

	@ApiModelProperty(notes = "ChapterName " , name = "ChapterName",required = true, value = "ChapterName")
	private String chaptername;
	
	@ApiModelProperty(notes = "Block " , name = "block",required = true, value = "block")
	private String block;
	
	@ApiModelProperty(notes = "mathDesign " , name = "mathDesign",required = true, value = "mathDesign")
	private String mathDesign;
	
	@ApiModelProperty(notes = "Knowledge " , name = "knowledge",required = true, value = "knowledge")
	private List<KnowledgeEntity> knowledges; 
	
	public ChapterResponse convertFromEntity(ChapterEntity entity, BlockEntity blockEnitity, List<KnowledgeEntity> knowledges) {
		mappingFields(entity);
		this.knowledges = knowledges;
		return this;		
	}
	public ChapterResponse convertFromEntity(ChapterEntity entity) {
		mappingFields(entity);	
		return this;		
	}
	
	private void mappingFields(ChapterEntity entity) {
		this.id = entity.getId();
		this.chaptername = entity.getChaptername();
		this.block = entity.getBlock();
		this.mathDesign = entity.getMathDesign();
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.version = entity.getVersion();
		this.url = entity.getUrl();
	}
}
