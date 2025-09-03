package com.nt.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Document(collection = "admins") // MongoDB collection name will be 'admins'
public class Admin implements UserDetails {

    @Id
    private String email; // Primary key (unique ID)

    private String name;

    private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

}
