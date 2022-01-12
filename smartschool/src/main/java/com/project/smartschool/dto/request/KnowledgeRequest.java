package com.project.smartschool.dto.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.project.smartschool.entities.KnowledgeEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class KnowledgeRequest {

	private String id;
	
	@NotBlank(message = "Nội dung không được để trống!")
	private String contents;

	@NotBlank(message = "Tên kiến thức không được để trống!")
	private String knowledgename;	
	
	private String chapter;

	private Integer status;
	
	private Set<String> requestQuestions = new HashSet<String>();
	
	public KnowledgeEntity convertToEntity() {
		KnowledgeEntity entity = new KnowledgeEntity();
		
		entity.setId(this.id);
		entity.setKnowledgeName(this.knowledgename);
		entity.setChapter(this.chapter);
		entity.setContents(this.contents);
		entity.setStatus(this.status);
		entity.setReviewQuestions(requestQuestions);
		return entity;
	}
	
	public KnowledgeEntity convertToEntity(KnowledgeEntity entity) {
		entity.setId(this.id);
		entity.setKnowledgeName(this.knowledgename);
		entity.setContents(this.contents);
		entity.setChapter(this.chapter);
		entity.setReviewQuestions(requestQuestions);
		entity.setStatus(this.status);
		return entity;
	}
	
}
