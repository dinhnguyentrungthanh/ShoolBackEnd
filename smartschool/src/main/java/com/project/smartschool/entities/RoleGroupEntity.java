package com.project.smartschool.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "role_group")
@ToString
@Setter
@Getter
public class RoleGroupEntity extends BaseEntity implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Field
	private Set<String> roles;
	
	@Field
	private String name;

}
