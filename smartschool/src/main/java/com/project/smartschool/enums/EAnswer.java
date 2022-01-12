package com.project.smartschool.enums;

public enum EAnswer {
	NONE("none"),
	ANSWER1("answer1"),
	ANSWER2("answer2"),
	ANSWER3("answer3"),
	ANSWER4("answer4");
	
	private final String value;

    private EAnswer(String value) {
        this.value = value;
        
    }
    
    public String getValue() {
		return value;
	}
}
