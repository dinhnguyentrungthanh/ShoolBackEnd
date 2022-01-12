package com.project.smartschool.entities;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.project.smartschool.enums.ERole;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "role")
@ToString
@Getter
@Setter
public class RoleEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Indexed(unique = true)
	private Integer code;
	
	@Indexed(unique = true)
	private ERole name;

}
