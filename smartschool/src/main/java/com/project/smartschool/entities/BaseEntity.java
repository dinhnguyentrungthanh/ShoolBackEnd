package com.project.smartschool.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	protected String id;

	@Field
	@CreatedBy
	protected String createdBy;

	@Field
	@CreatedDate
	protected Date createdDate;

	@Field
	@LastModifiedBy
	protected String modifiedBy;

	@Field
	@LastModifiedDate
	protected Date modifiedDate;

	@Version
	protected Integer version;
	
	@Field
	@JsonProperty(access = Access.WRITE_ONLY)
	protected boolean enable = true;

}
