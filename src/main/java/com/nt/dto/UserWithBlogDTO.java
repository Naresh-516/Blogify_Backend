package com.nt.dto;

import java.util.ArrayList;
import java.util.List;

import com.nt.entity.Blog;

import lombok.Data;

@Data
public class UserWithBlogDTO {
	 private String userId;
	 private String name;
	 private String email;
	 private String address;
	 private String mobile;
	 private List<Blog> blogs=new ArrayList<Blog>();


}