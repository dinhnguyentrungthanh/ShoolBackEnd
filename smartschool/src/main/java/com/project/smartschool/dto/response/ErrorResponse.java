package com.project.smartschool.dto.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Error Respone Model")
public class ErrorResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "Error Code", name = "code" , value = "200")
	private int code;
	
	@ApiModelProperty(notes = "Status", name = "message" , value = "SUCCESS")
	private String status;
	
	@ApiModelProperty(notes = "message", name = "message" , value = "invalid field")
	private String message;;
	
}
