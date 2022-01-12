package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.BlockEntity;
import com.project.smartschool.entities.TestTypeEntity;
import com.project.smartschool.enums.ETestType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TestTypeResponse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6496139099559926369L;

	private String id;
	
	@ApiModelProperty(notes = "Test Type Code" , name = "testTypeCode", required = true, value = "Test Type Code")
	private String testTypeCode;
	
	@ApiModelProperty(notes = "Test Type Name" , name = "testTypeName", required = true, value = "Test Type Name")
	private String testTypeName;
	
	@ApiModelProperty(notes = "time" , name = "time", required = true, value = "time")
	private long time;
	
	@ApiModelProperty(notes = "Block" , name = "block", required = true, value = "Block")
	private BlockEntity block;
	
	@ApiModelProperty(notes = "type" , name = "type", required = true, value = "type")
	private ETestType type;
	
	@ApiModelProperty(notes = "tests" , name = "tests", required = true, value = "tests")
	private Set<String> tests = new HashSet<String>();
	
	public TestTypeResponse convertFromEntity(TestTypeEntity entity, BlockEntity block) throws ParseException {
		mappingFields(entity);
		this.block = block;
		return this;
	}	
	
	private void mappingFields(TestTypeEntity entity) {
		this.id = entity.getId();
		this.testTypeName = entity.getTestTypeName();
		this.type = entity.getType();
		this.tests = entity.getTests();
		this.time = entity.getTime();
		
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.version = entity.getVersion();
	}
	
}
