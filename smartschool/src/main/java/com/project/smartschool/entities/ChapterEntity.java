package com.project.smartschool.entities;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection =  "chapter")
@Getter
@Setter
@ToString
public class ChapterEntity extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Field
	private String chaptername;
	
	@Indexed(unique = true)
	private String url;
	
	@Field
	private String block;
	
	@Field
	private String mathDesign;
	
	@Field
	private Set<String> knowledges;
}
