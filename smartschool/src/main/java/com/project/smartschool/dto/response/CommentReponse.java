package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Set;

import com.project.smartschool.constraints.DateConstraint;
import com.project.smartschool.entities.CommentEntity;
import com.project.smartschool.util.DateUtils;

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
public class CommentReponse implements Serializable {

	private static final long serialVersionUID = 2889468432725707669L;

	@ApiModelProperty(notes = "Id " , name = "id",required = true, value = "id")
	private String id;

	@ApiModelProperty(notes = "Content " , name = "content",required = true, value = "content")
	private String content;
	
	@ApiModelProperty(notes = "User " , name = "user",required = true, value = "user")
	private UserResponse user;
	
	@ApiModelProperty(notes = "TestId " , name = "testId",required = true, value = "testId")	
	private String testId;
	
	@ApiModelProperty(notes = "parentId " , name = "parentId",required = true, value = "parentId")
	private String parentId;
	
	@ApiModelProperty(notes = "CreatedDate " , name = "createdDate",required = true, value = "createdDate")
	private String createdDate;
	
	@ApiModelProperty(notes = "ModifiedDate " , name = "modifiedDate",required = true, value = "modifiedDate")
	private String modifiedDate;
	
	@ApiModelProperty(notes = "Status " , name = "status",required = true, value = "status")
	private Integer status;
	
	@ApiModelProperty(notes = "SubCommnet " , name = "subCommnet",required = true, value = "subCommnet")
	private Set<CommentReponse> subCommnet;

	public CommentReponse convertFromEntity(CommentEntity entity, UserResponse user, Set<CommentReponse> subCommnet) throws ParseException {
		mappingFields(entity);
		this.user = user;
		this.subCommnet = subCommnet;
		return this;
	}
	
	private void mappingFields(CommentEntity entity) throws ParseException {
		this.id = entity.getId();
		this.content = entity.getContent();
		this.parentId = entity.getParentId();
		this.testId = entity.getTestId();
		this.createdDate = DateUtils.getStringDate(entity.getCreatedDate(), DateConstraint.DD_MM_YYYY__HH_MM_SS);
		this.modifiedDate = DateUtils.getStringDate(entity.getModifiedDate(), DateConstraint.DD_MM_YYYY__HH_MM_SS);
		this.status = entity.getStatus();
	}
	
}
