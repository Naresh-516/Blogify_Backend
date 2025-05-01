package com.nt.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "users") // Collection name in MongoDB
public class Users {

    @Id
    private String id; // MongoDB uses String IDs (ObjectId)

    private String name;

    private String email;

    private String mobile;

    private String password;

    private String gender;

    private String address;

    @DBRef(lazy = false)
    private List<Blog> blogs=new ArrayList<Blog>();// Embedded documents or references

}