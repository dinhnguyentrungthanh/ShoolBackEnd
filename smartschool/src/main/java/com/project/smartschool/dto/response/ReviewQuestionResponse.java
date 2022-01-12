package com.project.smartschool.dto.response;

import java.io.Serializable;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.ReviewQuestionEntity;

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
public class ReviewQuestionResponse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 442084124850917117L;

	private String id;
	
	@ApiModelProperty(notes = "Review Question Name" , name = "reviewQuestionName", required = true, value = "Review Question Name")
	private String reviewQuestionName;
	
	@ApiModelProperty(notes = "Review Question Answer" , name = "reviewQuestionAnswer", required = true, value = "Review Question Answer")
	private String reviewQuestionAnswer;
	
	@ApiModelProperty(notes = "Time" , name = "time", required = true, value = "Time")
	private long time;
	
	public ReviewQuestionResponse convertFromEntity(ReviewQuestionEntity entity) {
		mappingFields(entity);
		return this;
	}
	
	private void mappingFields(ReviewQuestionEntity entity) {
		this.id = entity.getId();
		this.reviewQuestionName = entity.getReviewQuestionName();
		this.reviewQuestionAnswer = entity.getReviewQuestionAnswer();
		this.time = entity.getTime();
		this.createdBy = entity.getCreatedBy();
		this.modifiedBy = entity.getModifiedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedDate = entity.getModifiedDate();
	}
	
}
