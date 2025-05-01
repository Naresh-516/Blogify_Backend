package com.nt.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
	private String id;
	private String name;
	private String email;
	private String address;
	private String gender;
	private String mobile;

}