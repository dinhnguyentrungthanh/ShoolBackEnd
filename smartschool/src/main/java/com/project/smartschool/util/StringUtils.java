package com.project.smartschool.util;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtils {
	
	public static String generatorId() {
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    return RandomStringUtils.random(5, useLetters, useNumbers);
	}
}
