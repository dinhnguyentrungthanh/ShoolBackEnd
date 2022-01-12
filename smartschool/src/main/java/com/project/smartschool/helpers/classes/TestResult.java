package com.project.smartschool.helpers.classes;

import java.io.Serializable;

import com.project.smartschool.enums.EAnswer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestResult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String answer = EAnswer.NONE.getValue();
	private String answerCorrect = EAnswer.NONE.getValue();
}