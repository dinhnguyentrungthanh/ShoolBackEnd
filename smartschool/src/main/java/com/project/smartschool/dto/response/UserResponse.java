package com.project.smartschool.dto.response;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.project.smartschool.entities.BaseEntity;
import com.project.smartschool.entities.UserEntity;
import com.project.smartschool.enums.ELevel;

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
public class UserResponse extends BaseEntity implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes = "Id " , name = "id",required = true, value = "id")
	private String id;

	@ApiModelProperty(notes = "Username " , name = "username",required = true, value = "username")
	private String username;

	@ApiModelProperty(notes = "Fullname " , name = "fullname",required = true, value = "fullname")
	private String fullname;
	
	
	@ApiModelProperty(notes = "Address " , name = "address",required = true, value = "fullname")
	private String address;
	
	@ApiModelProperty(notes = "Phone" , name = "phone",required = true, value = "phone")
	private String phone;
	
	@ApiModelProperty(notes = "Email" , name = "email",required = true, value = "email")
	private String email;
	
	@ApiModelProperty(notes = "avatar" , name = "avatar",required = false, value = "avatar")
	private String avatar;
	
	@ApiModelProperty(notes = "Level" , name = "level",required = true, value = "level")
	private ELevel level;
	
	@ApiModelProperty(notes = "Birthday " , name = "birthday",required = true, value = "birthday")
	private Date birthday;
	
	@ApiModelProperty(notes = "Gender" , name = "gender",required = true, value = "gender")
	private int gender;

	@ApiModelProperty(notes = "Status " , name = "status",required = true, value = "status")
	private Integer status;
	
	@ApiModelProperty(notes = "Count " , name = "count",required = true, value = "count")
	private Integer count;

	@ApiModelProperty(notes = "RoleGroups " , name = "status",required = true, value = "roleGroups")
	private Set<String> roleGroups;

	@ApiModelProperty(notes = "Roles " , name = "status",required = true, value = "roles")
	private Set<String> roles; 
	
	@ApiModelProperty(notes = "Classes " , name = "classes",required = true, value = "classes")
	private Set<String> classes;
	
	public UserResponse convertFromEntity(UserEntity entity) {
		mappingFields(entity);
		return this;		
	}
	
	public UserResponse convertFromEntity(UserEntity entity, Set<String> roleGroups, Set<String> roles) {
		mappingFields(entity);
		this.roleGroups = roleGroups;	
		this.roles = roles;
		return this;		
	}
	
	private void mappingFields(UserEntity entity) {
		this.id = entity.getId();
		this.username = entity.getUsername();
		this.fullname = entity.getFullname();
		this.status = entity.getStatus();
		this.address = entity.getAddress();
		this.birthday = entity.getBirthday();
		this.gender = entity.getGender();
		this.phone = entity.getPhone();
		this.email = entity.getEmail();
		this.avatar = entity.getAvatar();
		this.level = entity.getLevel();
		this.createdBy = entity.getCreatedBy();
		this.createdDate = entity.getCreatedDate();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedDate = entity.getModifiedDate();
		this.count = entity.getCount();
		this.version = entity.getVersion();
	}

	public UserResponse convertFromEntity(UserEntity entity, Set<String> rgroups, Set<String> roles, Set<String> classes) {
		mappingFields(entity);
		this.roleGroups = rgroups;	
		this.roles = roles;
		this.classes = classes;
		return this;
	}
}
