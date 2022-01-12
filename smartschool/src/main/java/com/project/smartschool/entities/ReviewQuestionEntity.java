package com.project.smartschool.entities;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "review_question")
@ToString
@Getter
@Setter
public class ReviewQuestionEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Field
	private String reviewQuestionName;
	
	@Field
	private String reviewQuestionAnswer;

	@Field	
	private long time;
	
	@Field
	private String knowledge;
		
}
