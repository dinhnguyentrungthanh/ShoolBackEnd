package com.project.smartschool.dto.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.project.smartschool.entities.ChapterEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChapterRequest {

	private String id;
	
	@NotBlank(message = "Vui lòng nhập Tên Chương")
	private String chaptername;
	
	@NotBlank(message = "Vui lòng nhập Khối")
	private String block;
	
	@NotBlank(message = "Vui lòng nhập Dạng Toán")
	private String mathDesign;

	private Set<String> knowledges = new HashSet<String>();

	public ChapterEntity convertToEntity() {
		return mappingFields(new ChapterEntity());
	}

	public ChapterEntity convertToEntity(ChapterEntity entity) {
		return mappingFields(entity);
	}

	private ChapterEntity mappingFields(ChapterEntity entity) {
		entity.setId(this.id);
		entity.setChaptername(this.chaptername);
		entity.setBlock(this.block);
		entity.setMathDesign(this.mathDesign);
		entity.setKnowledges(this.knowledges);
		return entity;
	}
}
