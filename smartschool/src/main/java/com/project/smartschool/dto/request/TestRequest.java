package com.project.smartschool.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.project.smartschool.entities.TestEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TestRequest {

	private String id;
	
	@NotBlank(message = "Câu hỏi không được để trống!")
	private String question;

	@NotBlank(message = "Câu trả lời không được để trống !")
	private String answerCorrect;

	@NotBlank(message = "Câu hỏi không được để trống!")
	private String answer1;
	
	@NotBlank(message = "Câu hỏi không được để trống!")
	private String answer2;
	
	@NotBlank(message = "Câu hỏi không được để trống!")
	private String answer3;
	
	@NotBlank(message = "Câu hỏi không được để trống!")
	private String answer4;
	
	@NotBlank(message = "Bài giải không được để trống!")
	private String answerEssay;
	
//	@NotNull(message = "Điểm không được để trống không được để trống!")
//	@Min(value = 0, message = "Điểm phải lớn hơn 0")
//	@Max(value = 10, message = "Điểm phải nhỏ hơn 10")
//	private Double point;
	
	@NotBlank(message = "Loại bài kiểm tra không được để trống không được để trống!")
	private String testType;
	
	public TestEntity convertToEntity() {
		return mappingFields(new TestEntity());
	}
	
	public TestEntity convertToEntity(TestEntity entity) {
		return mappingFields(entity);
	}
	
	private TestEntity mappingFields(TestEntity entity) {
		entity.setId(this.id);
		entity.setQuestion(this.question);
		entity.setAnswerCorrect(this.answerCorrect);
		entity.setTestType(this.testType);
		entity.setAnswer1(this.answer1);
		entity.setAnswer2(this.answer2);
		entity.setAnswer3(this.answer3);
		entity.setAnswer4(this.answer4);
		entity.setAnswerEssay(this.answerEssay);
		return entity;
	}
	
}
