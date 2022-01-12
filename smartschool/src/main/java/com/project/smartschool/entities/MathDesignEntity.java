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

@Document(collection =  "mathsdesign")
@Getter
@Setter
@ToString
public class MathDesignEntity extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Indexed(unique = true)
	private String mathDesignName;
	
	@Indexed(unique = true)
	private String url;
	
	@Field
	private Set<String> chapters = new HashSet<String>();

	@Field
	private String major;

}
