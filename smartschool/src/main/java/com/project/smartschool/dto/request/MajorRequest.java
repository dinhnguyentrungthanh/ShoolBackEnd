package com.project.smartschool.dto.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.project.smartschool.entities.MajorEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MajorRequest {

	private String id;

	@NotBlank(message = "Vui lòng nhập Môn")
	private String majorname;

	@NotBlank(message = "Vui lòng nhập Khối")
	private String block;

	private Set<String> mathDesigns = new HashSet<String>();

	public MajorEntity convertToEntity() {
		return mappingFields(new MajorEntity());
	}

	public MajorEntity convertToEntity(MajorEntity entity) {
		return mappingFields(entity);
	}

	private MajorEntity mappingFields(MajorEntity entity) {
		entity.setId(this.id);
		entity.setMajorname(this.majorname);
		entity.setMathDesigns(this.mathDesigns);
		entity.setBlock(this.block);
		return entity;
	}
}
