package com.project.smartschool.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePassRequest {
	
	@NotBlank(message = "Vui lòng nhập Mật khẩu")
	@Size(min = 5, max = 100, message = "Mật khẩu phải có độ dài từ {min} đến {max} kí tự")
	private String password;
	
	@NotBlank(message = "Vui lòng nhập User Id")
	private String user;
}
