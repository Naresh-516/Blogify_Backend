package com.nt.service;

import java.util.List;
import java.util.Set;

import com.nt.dto.BlogRequestDTO;
import com.nt.dto.BlogResponseDTO;
import com.nt.entity.Comment;
import com.nt.entity.Users;

public interface IBlogService {
 public BlogResponseDTO createBlog(BlogRequestDTO dto,String email); 
 public List<BlogResponseDTO> getAllBlogs();
 public List<BlogResponseDTO> getBlogsByUser(String email);
 public List<BlogResponseDTO> getBlogsByUser02(String UserId);
 public BlogResponseDTO updatedBlog(String blogId,BlogRequestDTO dto,String email);
 public void deletedBlog(String blogId);
 public void admindeleteBlog(String blogId);
 public List<BlogResponseDTO> getDeletedBlogs();
 public BlogResponseDTO toggleLike(String blogId, String email);
 public BlogResponseDTO addComment(String blogId,String email,String content);
 public BlogResponseDTO editComment(String blogId,String email,String content,String commentid);
 public BlogResponseDTO deleteComment(String blogId,String email,String commentid);
 
}
