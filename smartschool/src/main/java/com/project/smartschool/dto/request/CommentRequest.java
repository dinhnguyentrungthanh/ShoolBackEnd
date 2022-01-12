package com.project.smartschool.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.project.smartschool.entities.CommentEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CommentRequest {

	private String id;
	
	@NotBlank(message = "Nội dung không được để trống!")
	private String content;

	@NotNull(message = "Parrent commnet không được null!")
	private String parentId;

	@NotBlank(message = "Tên tài khoản không được để trống!")
	private String username;

	@NotBlank(message = "Bài kiểm tra không được để trống!")
	private String testId;
	
	private Integer status;
	
	public CommentEntity convertToEntity() {
		CommentEntity entity = new CommentEntity();
		
		entity.setId(this.id);
		entity.setContent(this.content);
		entity.setUsername(this.username);
		entity.setTestId(this.testId);
		entity.setParentId(this.parentId);
		entity.setStatus(this.status);
		
		return entity;
	}
	
	public CommentEntity convertToEntity(CommentEntity entity) {
		entity.setId(this.id);
		entity.setContent(this.content);
		entity.setUsername(this.username);
		entity.setTestId(this.testId);
		entity.setParentId(this.parentId);
		entity.setStatus(this.status);
		
		return entity;
	}
	
}
