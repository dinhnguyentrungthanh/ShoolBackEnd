package com.project.smartschool.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

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
public class ChapterOfKnowledgeRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Vui lòng nhập Khối")
	private String block;
	
	@NotBlank(message = "Vui lòng nhập Dạng Toán")
	private String mathDesign;
	
	@NotBlank(message = "Vui lòng nhập Chương")
	private String chapter;
	
	@NotBlank(message = "Vui lòng nhập Nội dung kiến thức")
	private String contents;
	
	@NotBlank(message = "Vui lòng nhập Tiêu đề")
	private String knowledgeName;
}
