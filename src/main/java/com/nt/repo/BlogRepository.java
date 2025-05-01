package com.nt.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nt.entity.Blog;
import com.nt.entity.Users;

public interface BlogRepository extends MongoRepository<Blog,String>{
List<Blog> findByUser(Users user); 
}
