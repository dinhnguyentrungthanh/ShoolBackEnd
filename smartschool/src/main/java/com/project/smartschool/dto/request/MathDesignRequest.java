package com.project.smartschool.dto.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.project.smartschool.entities.MathDesignEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MathDesignRequest {

	private String id;

	@NotBlank(message = "Vui lòng nhập Dạng Toán")
	private String mathDesignName;
	
	@NotBlank(message = "Vui lòng nhập Môn")
	private String major;

	private Set<String> chapters = new HashSet<String>();

	public MathDesignEntity convertToEntity() {
		return mappingFields(new MathDesignEntity());
	}

	public MathDesignEntity convertToEntity(MathDesignEntity entity) {
		return mappingFields(entity);
	}

	private MathDesignEntity mappingFields(MathDesignEntity entity) {
		entity.setId(this.id);
		entity.setMathDesignName(this.mathDesignName);
		entity.setChapters(this.chapters);
		entity.setMajor(this.major);
		return entity;
	}
}
