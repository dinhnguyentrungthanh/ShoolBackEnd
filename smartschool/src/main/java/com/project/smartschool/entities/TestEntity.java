package com.project.smartschool.entities;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "test")
@ToString
@Getter
@Setter
public class TestEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1426887976415114718L;

	@Field
	private String question;
		
	@Field
	private String answerCorrect;
	
	@Field
	private String answer1;
	
	@Field
	private String answer2;
	
	@Field
	private String answer3;
	
	@Field
	private String answer4;
	
	@Field
	private Double point;
	
	@Field
	private String answerEssay;
	
	@Field
	private String testType;
	
}
