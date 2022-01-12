package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.text.ParseException;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.TestEntity;
import com.project.smartschool.entities.TestTypeEntity;

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
public class TestResponse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	@ApiModelProperty(notes = "Question" , name = "question", required = true, value = "Question")
	private String question;
	
	@ApiModelProperty(notes = "Answer" , name = "answer", required = true, value = "Answer")
	private String answerCorrect;
	
	@ApiModelProperty(notes = "Answer" , name = "answer", required = true, value = "Answer")
	private String answer1;
	
	@ApiModelProperty(notes = "Answer" , name = "answer", required = true, value = "Answer")
	private String answer2;
	
	@ApiModelProperty(notes = "Answer" , name = "answer", required = true, value = "Answer")
	private String answer3;

	@ApiModelProperty(notes = "Answer" , name = "answer", required = true, value = "Answer")
	private String answer4;	
	
	@ApiModelProperty(notes = "Point" , name = "point", required = true, value = "Point")
	private Double point;
	
	@ApiModelProperty(notes = "answerEssay" , name = "answerEssay", required = true, value = "answerEssay")
	private String answerEssay;	
	
	@ApiModelProperty(notes = "Test Type Id" , name = "testTypeId", required = true, value = "Test Type Id")
	private TestTypeEntity testType;
	
	public TestResponse convertFromEntity(TestEntity entity) throws ParseException {
		this.mappingFields(entity);
		return this;
	}
	
	public TestResponse convertFromEntity(TestEntity entity, TestTypeEntity testType) throws ParseException {
		this.mappingFields(entity);
		this.testType = testType;
		return this;
	}
	
	private void mappingFields(TestEntity entity) throws ParseException {
		this.id = entity.getId();
		this.question = entity.getQuestion();
		this.answerCorrect = entity.getAnswerCorrect();
		this.answer1 = entity.getAnswer1();
		this.answer2 = entity.getAnswer2();
		this.answer3 = entity.getAnswer3();
		this.answer4 = entity.getAnswer4();
		this.point = entity.getPoint();
		this.answerEssay = entity.getAnswerEssay();
		
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.version = entity.getVersion();
	}
	
}
