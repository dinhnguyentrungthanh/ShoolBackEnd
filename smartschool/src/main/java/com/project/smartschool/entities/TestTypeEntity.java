package com.project.smartschool.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.project.smartschool.enums.ETestType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "test_type")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestTypeEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7573433347008408340L;
	
	@Field
	private String testTypeName;
	
	@Field
	private String block;
	
	@Field
	private ETestType type;
	
	@Field
	private long time;
	
	@Field
	private Set<String> tests = new HashSet<String>();
		
}
