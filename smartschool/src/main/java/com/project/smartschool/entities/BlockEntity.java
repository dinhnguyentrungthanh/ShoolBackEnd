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

@Document(collection =  "block")
@Getter
@Setter
@ToString
public class BlockEntity extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Indexed(unique = true)
	private String blockname;
	
	@Indexed(unique = true)
	private String url;
	
	@Field
	private Set<String> majors = new HashSet<String>();
	
	@Field
	private Set<String> classes = new HashSet<String>();
	
	
}
