package com.nt.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nt.entity.DeletedBlogs;

public interface DeletedBlogRepository extends MongoRepository<DeletedBlogs,String>{

}