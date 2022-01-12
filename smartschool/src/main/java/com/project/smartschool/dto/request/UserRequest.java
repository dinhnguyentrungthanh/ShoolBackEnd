package com.project.smartschool.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.enums.ELevel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	protected String id;
	
	@NotBlank(message = "Vui lòng nhập Tài Khoản")
	@Size(min = 5, max = 100, message = "Tài Khoản phải có độ dài từ {min} đến {max} kí tự")
	protected String username;

	@NotBlank(message = "Vui lòng nhập Tên đầy đủ")
	@Size(min = 5, max = 100, message = "Tên đầy phải có độ dài từ {min} đến {max} kí tự")
	protected String fullname;

//	@NotBlank(message = "Vui lòng nhập Địa chỉ")
//	@Size(min = 5, max = 100, message = "Vui lòng nhập Địa chỉ có độ dài từ {min} đến {max} kí tự")
	protected String address;

	@Pattern(regexp = "^$|(84|0[3|5|7|8|9])+([0-9]{8})", message = "Vui lòng nhập Số điện thoại đúng định dạng")
	protected String phone;

	@Email(message = "Vui lòng nhập email đúng định dạng")
	protected String email;

	@JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
	protected Date birthday;

	protected ELevel level = ELevel.STUDENT;

	protected int gender;

	protected Integer status;
	
	protected Integer count;

	protected Set<String> roleGroups = new HashSet<String>();

	protected Set<String> roles = new HashSet<String>();

	protected Set<String> classes = new HashSet<String>();
	
	public UserEntity convertToEntity() {
		return mapping(new UserEntity());
	}

	public UserEntity convertFromEntity(UserEntity entity) {
		mapping(entity);
		return entity;
	}

	public UserEntity convertFromEntity(UserEntity entity, Set<String> roleGroups, Set<String> roles) {
		mappingFields(entity);
		this.roleGroups = roleGroups;
		this.roles = roles;
		return entity;
	}

	protected void mappingFields(UserEntity entity) {
		this.id = entity.getId();
		this.username = entity.getUsername();
		this.fullname = entity.getFullname();
		this.status = entity.getStatus();
		this.address = entity.getAddress();
		this.birthday = entity.getBirthday();
		this.gender = entity.getGender();
		this.phone = entity.getPhone();
		this.email = entity.getEmail();
		this.level = entity.getLevel();
		this.count = entity.getCount();
	}
	
	protected UserEntity mapping(UserEntity entity) {
		entity.setId(this.id);
		entity.setUsername(this.username);
		entity.setFullname(this.fullname);
		entity.setStatus(this.status);
		entity.setAddress(this.address);
		entity.setBirthday(this.birthday);
		entity.setGender(this.gender);
		entity.setPhone(this.phone);
		entity.setEmail(this.email);
		entity.setLevel(this.level);
		entity.setCount(this.count);
		entity.setClasses(this.classes);
		entity.setRoleGroups(this.roleGroups);
		return entity;
	}
	
	public UserEntity convertToEntity(UserEntity entity) {
		mappingFields(entity);
		return entity;
	}

	public UserEntity convertToEntity(UserEntity entity, Set<String> rgroups, Set<String> roles,
			Set<String> classes) {
		mappingFields(entity);
		this.roleGroups = rgroups;
		this.roles = roles;
		this.classes = classes;
		return entity;
	}
}
