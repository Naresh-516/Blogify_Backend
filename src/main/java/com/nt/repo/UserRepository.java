package com.nt.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nt.entity.Users;
public interface UserRepository extends MongoRepository<Users,String> {
	Optional<Users> findByEmail(String email);
	Optional<Users> findById(String userId);
}
