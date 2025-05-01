package com.nt.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "admins") // MongoDB collection name will be 'admins'
public class Admin {

    @Id
    private String email; // Primary key (unique ID)

    private String name;

    private String password;

}
