package com.project.smartschool.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "class")
@Getter
@Setter
@ToString
public class ClassEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Indexed(unique = true)
	private String classname;

	@Field
	private String block;
	
	@Field
	private Set<String> users = new HashSet<String>();;
}
