package com.nt.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
public class Comment {

    @Id
    private String commentId;

    private String blogId; 

    private String userId;

    private String username;

    private String content;

    private Date timestamp;
}
