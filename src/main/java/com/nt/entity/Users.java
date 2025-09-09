package com.nt.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
@Document(collection = "users") // Collection name in MongoDB
public class Users implements UserDetails {

    @Id
    private String id; // MongoDB uses String IDs (ObjectId)

    private String name;

    private String email;

    private String mobile;

    private String password;

    private String gender;

    private String address;

//    @DBRef(lazy = false)
//    @JsonIgnore
//    private List<Blog> blogs=new ArrayList<Blog>();// Embedded documents or references

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Users)) return false;
	        Users other = (Users) o;
	        return id != null && id.equals(other.getId());
	    }

	    @Override
	    public int hashCode() {
	        return id != null ? id.hashCode() : 0;
	    }

}