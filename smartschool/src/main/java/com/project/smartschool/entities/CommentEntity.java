package com.project.smartschool.entities;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "comment")
@ToString
@Getter
@Setter
public class CommentEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Field
	private String content;

	@Field
	private String parentId;

	@Field
	private String username;

	@Field
	private String testId;

	@Field
	private Integer status;

	
}
