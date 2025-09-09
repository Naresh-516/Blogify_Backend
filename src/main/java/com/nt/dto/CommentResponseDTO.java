package com.nt.dto;

import java.util.Date;

import lombok.Data;
@Data
public class CommentResponseDTO {
	 private String username;
	 private String content;
	 private Date timestamp;

}
