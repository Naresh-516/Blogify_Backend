package com.nt.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "deleted_blogs") // MongoDB collection name
public class DeletedBlogs {

    @Id
    private String id; // MongoDB IDs are usually String/ObjectId

    private String originalId; // keeping original blog's id (also as String)

    private String title;

    private String content;

    private String tags;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private String userId; // Mongo IDs are Strings, not Longs

    private String userName;
}
