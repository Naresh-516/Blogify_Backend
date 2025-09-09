package com.nt.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
@Document(collection = "blogs") // MongoDB collection will be 'blogs'
public class Blog {
	public Blog() {
	    this.likedUsers = new HashSet<>();
	    this.comments=new ArrayList<>();
	}
    @Id
    private String id;  // MongoDB IDs are usually Strings (ObjectId)

    private String title;

    private String content;

    private String tags;

    private String userid; 

    private LocalDateTime postedAt;

    private LocalDateTime updatedAt;
    
    private Set<String> likedUsers;
    private int likeCount;
    
    private List<Comment> comments;

	public void setLikedUsers(Object likedUsers2) {
	}

}
