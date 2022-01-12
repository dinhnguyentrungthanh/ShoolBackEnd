package com.project.smartschool.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.project.smartschool.enums.ELevel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "user")
@ToString
@Getter
@Setter
@ApiModel(description = "User Model")
public class UserEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Indexed(unique = true)
	private String username;

	@Field
	private String password;

	@Field
	private String fullname;
	
	@Field
	private String address;
	
	@Field
	private String phone;
	
	@Field
	private String avatar = "default.svg";
	
	@Field
	private String email;
	
	@Field
	private Date birthday;
	
	@Field
	private Integer gender;

	@Field
	private ELevel level;
	
	@Field
	private Integer status;

	@Field
	private Integer count = 0;

	@Field
	private Set<String> roleGroups = new HashSet<String>();;
	
	@Field
	private Set<String> classes = new HashSet<String>();	

}
