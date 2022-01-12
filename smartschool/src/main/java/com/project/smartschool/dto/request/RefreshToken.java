package com.project.smartschool.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String refreshToken;

}
