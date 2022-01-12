package com.project.smartschool.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Field;

import com.project.smartschool.entities.PointEntity;
import com.project.smartschool.helpers.classes.TestResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	@NotBlank(message = "Tên tài khoản không được để trống!")
	private String user;

	@NotBlank(message = "Bài kiểm tra không được để trống!")
	private String testType;
	
	private List<TestResult> testMemo = new ArrayList<TestResult>();
	
	private String answerEssay;
	
	private boolean completed = false;
	
	@Field
	private Double point;
	
	public PointEntity convertToEntity() {	
		return mappingFields(new PointEntity());
	}
	
	public PointEntity convertToEntity(PointEntity entity) {
		return mappingFields(entity);
	}
	
	private PointEntity mappingFields(PointEntity entity) {
		entity.setId(this.id);
		entity.setUser(this.user);
		entity.setTestType(this.testType);
		entity.setPoint(this.point);
		entity.setCompleted(this.completed);
		
		entity.setTestMemo(this.testMemo);
		entity.setAnswerEssay(this.answerEssay);
		return entity;
	}
	
}

