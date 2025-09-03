package com.nt.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nt.entity.Blog;

public interface BlogRepository extends MongoRepository<Blog,String>{
	List<Blog> findByUserid(String userid);
}
