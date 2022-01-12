package com.project.smartschool.entities;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection =  "major")
@Getter
@Setter
@ToString
public class MajorEntity extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String majorname;
	
	@Indexed(unique = true)
	private String url;
	
	@Field
	private Set<String> mathDesigns;
	
	@Field
	private String block;

}
