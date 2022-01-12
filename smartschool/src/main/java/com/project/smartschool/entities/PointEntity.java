package com.project.smartschool.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.project.smartschool.enums.ETestType;
import com.project.smartschool.helpers.classes.TestResult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection =  "point")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PointEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3885474820212090678L;

	@Field
	private String user;
	
	@Field
	private String testType;
	
	@Field
	private List<TestResult> testMemo = new ArrayList<TestResult>();
	
	@Field
	private Double point;
	
	@Field 
	private String answerEssay;
	
	@Field 
	private ETestType type;
	
	@Field 
	private boolean completed;
	
	public void calculatePoint() {
		this.point = Double.valueOf(testMemo.stream().filter(t -> t.getAnswer().equals(t.getAnswerCorrect())).count()) / testMemo.size() * 10;
	}
	
}


