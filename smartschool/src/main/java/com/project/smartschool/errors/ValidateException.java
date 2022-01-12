package com.project.smartschool.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

	public ValidateException(String message) {
		super(message);
	}

}
