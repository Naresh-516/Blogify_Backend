package com.nt.service;

import java.util.List;

import com.nt.dto.BlogRequestDTO;
import com.nt.dto.BlogResponseDTO;

public interface IBlogService {
 public BlogResponseDTO createBlog(BlogRequestDTO dto); 
 public List<BlogResponseDTO> getAllBlogs();
 public List<BlogResponseDTO> getBlogsByUser(String userId);
 public BlogResponseDTO updatedBlog(String blogId,BlogRequestDTO dto);
 public void deletedBlog(String blogId);
 public void admindeleteBlog(String blogId);
 public List<BlogResponseDTO> getDeletedBlogs();
 
}
