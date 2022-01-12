package com.project.smartschool.dto.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.project.smartschool.entities.ClassEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ClassRequest {

	private String id;

	@NotBlank(message = "Vui lòng nhập Lớp")
	private String classname;

	@NotBlank(message = "Vui lòng nhập Khối")
	private String block;

	private Set<String> users = new HashSet<String>();

	public ClassEntity convertToEntity() {
		return mappingFields(new ClassEntity());
	}

	public ClassEntity convertToEntity(ClassEntity entity) {
		return mappingFields(entity);
	}

	private ClassEntity mappingFields(ClassEntity entity) {
		entity.setId(this.id);
		entity.setClassname(this.classname.trim());
		entity.setBlock(this.block);
		entity.setUsers(this.users);
		return entity;
	}
}
