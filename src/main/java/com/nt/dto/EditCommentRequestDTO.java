package com.nt.dto;

import lombok.Data;

@Data
public class EditCommentRequestDTO {
	private String commentId;
	private String blogId;
	private String updatedContent;
}
