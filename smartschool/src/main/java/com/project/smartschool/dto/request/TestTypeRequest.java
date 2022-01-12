package com.project.smartschool.dto.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.project.smartschool.entities.TestTypeEntity;
import com.project.smartschool.enums.ETestType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TestTypeRequest {

	private String id;
	
	@NotBlank(message = "Loại bài kiểm tra không được để trống không được để trống!")
	private String testTypeName;

	@NotBlank(message = "Khối không được để trống!")
	private String block;
	
	@Min(value = 1)
	@Max(value = 7200)
	private long time;

	@NotNull(message = "Kiểu không được để trống!")
	private ETestType type;
	
	private Set<String> tests = new HashSet<String>();
		
	public TestTypeEntity convertToEntity() {	
		return mappingFields(new TestTypeEntity());
	}
	
	public TestTypeEntity convertToEntity(TestTypeEntity entity) {		
		return mappingFields(entity);
	}
	
	private TestTypeEntity mappingFields(TestTypeEntity entity) {
		entity.setId(this.id);
		entity.setTestTypeName(this.testTypeName);
		entity.setBlock(this.block);
		entity.setType(this.type);
		entity.setTests(this.tests);
		entity.setTime(this.time);
		return entity;
	}
	
}
