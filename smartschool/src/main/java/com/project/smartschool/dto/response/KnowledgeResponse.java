package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.ChapterEntity;
import com.project.smartschool.entities.KnowledgeEntity;

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
public class KnowledgeResponse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Id " , name = "id",required = true, value = "id")
	private String id;
	
	@ApiModelProperty(notes = "Contents " , name = "contents",required = true, value = "Contents")
	private String contents;
	
	@ApiModelProperty(notes = "Url " , name = "url",required = true, value = "Url")
	private String url;
	
	@ApiModelProperty(notes = "KnowledgeName " , name = "knowledgeName",required = true, value = "KnowledgeName")
	private String knowledgeName;
	
	@ApiModelProperty(notes = "Chapter " , name = "chapter",required = true, value = "Chapter")
	private String chapter;
	
	@ApiModelProperty(notes = "MathDesign " , name = "mathDesign",required = true, value = "mathDesign")
	private String mathDesign;
	
	@ApiModelProperty(notes = "Block " , name = "block",required = true, value = "Block")
	private String block;
	
	@ApiModelProperty(notes = "ReviewQuestion " , name = "reviewQuestion",required = true, value = "ReviewQuestion")
	private Set<ReviewQuestionResponse> reviewQuestions;
	
	@ApiModelProperty(notes = "Status " , name = "status",required = true, value = "status")
	private Integer status;
	
	public KnowledgeResponse convertFromEntity(KnowledgeEntity entity, Set<ReviewQuestionResponse> reviewQuestions) {
		mappingFields(entity);
		this.reviewQuestions = reviewQuestions;
		return this;
	}
	
	public KnowledgeResponse convertFromEntity(KnowledgeEntity entity, ChapterEntity chapter, Set<ReviewQuestionResponse> reviewQuestion) {
		mappingFields(entity);
		this.reviewQuestions = reviewQuestion;
		this.chapter = chapter.getId();
		return this;
	}
	
	private void mappingFields(KnowledgeEntity entity) {
		this.id = entity.getId();
		this.contents = entity.getContents();
		this.url = entity.getUrl();
		this.knowledgeName = entity.getKnowledgeName();
		this.createdDate = entity.getCreatedDate();
		this.modifiedDate = entity.getModifiedDate();
		this.status = entity.getStatus();
		this.chapter = entity.getChapter();
	}
	
	public KnowledgeResponse mappingDeepFileds(String block, String mathDesign) {
		this.block = block;
		this.mathDesign = mathDesign;
		return this;
	}
	
}
