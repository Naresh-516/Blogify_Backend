package com.nt.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BlogResponseDTO {
	private String userId;
	private String id;
	private String title;
	private String content;
	private String tags;
	private String userName;
	private LocalDateTime postedAt;
	private LocalDateTime updatedAt;

}