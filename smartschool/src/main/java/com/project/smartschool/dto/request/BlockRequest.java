package com.project.smartschool.dto.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.project.smartschool.entities.BlockEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockRequest {

	private String id;

	@NotBlank(message = "Vui lòng nhập Tên Khối")
	private String blockname;

	//@NotEmpty(message = "Vui lòng nhập Môn")
	private Set<String> majors = new HashSet<String>();
	
	//@NotEmpty(message = "Vui lòng nhập Lớp")
	private Set<String> classes = new HashSet<String>();

	public BlockEntity convertToEntity() {
		return mappingFields(new BlockEntity());
	}

	public BlockEntity convertToEntity(BlockEntity entity) {
		return mappingFields(entity);
	}

	private BlockEntity mappingFields(BlockEntity entity) {
		entity.setId(this.id);
		entity.setBlockname(this.blockname.trim());
		entity.setMajors(this.majors);
		entity.setClasses(this.classes);
		return entity;
	}
}
