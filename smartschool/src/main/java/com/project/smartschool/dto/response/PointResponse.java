package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.PointEntity;
import com.project.smartschool.entities.TestTypeEntity;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.enums.ETestType;
import com.project.smartschool.helpers.classes.TestResult;

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
public class PointResponse extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -6280629921366448340L;
	
	private String id;
	
	@ApiModelProperty(notes = "User" , name = "User", required = true, value = "User")
	private UserEntity user;
	
	@ApiModelProperty(notes = "testTypeId" , name = "testTypeId", required = true, value = "testTypeId")
	private TestTypeEntity testType;
	
	@ApiModelProperty(notes = "testMemo" , name = "testMemo", required = true, value = "testMemo")
	private List<TestResult> testMemo = new ArrayList<TestResult>();
	
	@ApiModelProperty(notes = "point" , name = "point", required = true, value = "point")
	private Double point;
	
	@ApiModelProperty(notes = "timeLeft" , name = "timeLeft", required = true, value = "timeLeft")
	private Long timeLeft;
	
	@ApiModelProperty(notes = "answerEssay" , name = "answerEssay", required = true, value = "answerEssay")
	private String answerEssay;
	
	@ApiModelProperty(notes = "completed" , name = "completed", required = true, value = "completed")
	private boolean completed;
	
	@ApiModelProperty(notes = "type" , name = "type", required = true, value = "type")
	private ETestType type;
	
	public PointResponse convertFromEntity(PointEntity entity, UserEntity user, TestTypeEntity testType) throws ParseException {		
		this.user = user;
		this.testType = testType;
		this.mappingFields(entity);
		return this;
	}
	
	private void mappingFields(PointEntity entity) throws ParseException {
		this.id = entity.getId();
		this.point = entity.getPoint();
		this.testMemo = entity.getTestMemo();
		this.timeLeft = calculateTimeLeft(entity);
		this.answerEssay = entity.getAnswerEssay();
		this.completed = entity.isCompleted();
		this.type = entity.getType();
		
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.version = entity.getVersion();

	}
	
	private long calculateTimeLeft(PointEntity entity) {
		long timeLeft= (entity.getCreatedDate().getTime()  - new Date().getTime()) / 1000 + testType.getTime();
		if(timeLeft <= 0) {
			return 0;
		}
		return timeLeft;
	}

}
