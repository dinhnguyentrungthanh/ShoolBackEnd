package com.project.smartschool.dto.request;

import java.text.ParseException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.project.smartschool.entities.ReviewQuestionEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReviewQuestionRequest {

	private String id;

	@NotBlank(message = "Nội dung câu hỏi không được để trống!")
	private String reviewQuestionName;

	@NotBlank(message = "Nội dung câu trả lời không được để trống!")
	private String reviewQuestionAnswer;
	
	@NotNull(message = "Thời gian không được để trống!")
	@Min(value = 1, message = "Thời gian phải lớn hơn 0!")
	private long time;
	
	@NotBlank(message = "Mã kiến thức không được để trống!")
	private String knowledge;
	
	public ReviewQuestionEntity convertToEntity(){
		return mappingFields(new ReviewQuestionEntity());
	}
	
	public ReviewQuestionEntity convertToEntity(ReviewQuestionEntity entity){
		return mappingFields(entity);
	}
	
	private ReviewQuestionEntity mappingFields(ReviewQuestionEntity entity) {
		entity.setId(this.id);
		entity.setReviewQuestionName(this.reviewQuestionName);
		entity.setReviewQuestionAnswer(this.reviewQuestionAnswer);
		entity.setTime(this.time);
		entity.setKnowledge(this.knowledge);
		return entity;
	}
	
	
}
