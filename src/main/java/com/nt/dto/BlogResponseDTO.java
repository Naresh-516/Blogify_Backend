package com.nt.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.nt.entity.Comment;

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
	private int likeCount;
	private Set<String> likedUsers;
	private List<Comment> comments;
}