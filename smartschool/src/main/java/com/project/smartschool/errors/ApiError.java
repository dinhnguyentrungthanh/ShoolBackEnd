package com.project.smartschool.errors;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Getter
@Setter
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ApiError {
	
	private long timestamp = new Date().getTime();
    private HttpStatus status;
    private int code;
    private String message;
    private Map<String, String> errors;
    private String path;

    public ApiError(HttpStatus status, String message, Map<String, String> errors) {
        super();
        this.status = status;
        this.code = status.value();
        this.message = message;
        this.errors = errors;
    }
    
    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.code = status.value();
        this.message = message;
    }

}