package com.nt.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nt.entity.Admin;

public interface AdminRepository extends MongoRepository<Admin,String>{
	 Optional<Admin> findByEmail(String email);

}