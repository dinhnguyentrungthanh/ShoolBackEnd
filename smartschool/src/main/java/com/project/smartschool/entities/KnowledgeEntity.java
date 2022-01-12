package com.project.smartschool.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "knowledge")
@ToString
@Getter
@Setter
public class KnowledgeEntity extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Field
	@TextIndexed
	private String knowledgeName;

	@Field
	private String contents;
	
	@Field
	@Indexed(unique = true)
	private String url;
	
	@Field
	private String chapter;
		
	@Field
	private Integer status;
	
	@Field 
	private Set<String> reviewQuestions = new HashSet<String>();
	
}
