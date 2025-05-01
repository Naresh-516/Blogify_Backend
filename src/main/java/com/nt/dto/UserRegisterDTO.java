package com.nt.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
	private String name;
	private String email;
	private String password;
	private String address;
	private String gender;
    private String mobile;
}
