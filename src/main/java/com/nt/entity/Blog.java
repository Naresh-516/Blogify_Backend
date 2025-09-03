package com.nt.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Document(collection = "blogs") // MongoDB collection will be 'blogs'
public class Blog {

    @Id
    private String id;  // MongoDB IDs are usually Strings (ObjectId)

    private String title;

    private String content;

    private String tags;

    private String userid; 

    private LocalDateTime postedAt;

    private LocalDateTime updatedAt;

}
