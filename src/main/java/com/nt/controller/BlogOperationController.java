package com.nt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.dto.BlogRequestDTO;
import com.nt.dto.BlogResponseDTO;
import com.nt.service.BlogServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:5173") 
@RequestMapping("/blog")
public class BlogOperationController {
	@Autowired
	private BlogServiceImpl blogserv;
	@PostMapping("/create")
	public ResponseEntity<BlogResponseDTO> postBlog(@RequestBody BlogRequestDTO dto){
		
	 return ResponseEntity.ok(blogserv.createBlog(dto));
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<BlogResponseDTO>> getBlogsByUser(@PathVariable String userId){
		return ResponseEntity.ok(blogserv.getBlogsByUser(userId));
	}
	@GetMapping("/allblogs")
	public ResponseEntity<List<BlogResponseDTO>> getAllBlogs(){
		return ResponseEntity.ok(blogserv.getAllBlogs());
	}
	@PutMapping("/update/{blogId}")
	public ResponseEntity<BlogResponseDTO> updateBlog(@PathVariable String blogId,@RequestBody BlogRequestDTO dto){
		return ResponseEntity.ok(blogserv.updatedBlog(blogId, dto));
	}
	@DeleteMapping("/delete/blogid/{blogId}")
	public ResponseEntity<String> deleteBlog(@PathVariable String blogId){
		blogserv.deletedBlog(blogId);
		return ResponseEntity.ok("Blog deleted Successfully");	
	}
	@DeleteMapping("/admindelete/blogid/{blogId}")
	public ResponseEntity<String> admindeletedBlog(@PathVariable String blogId){
		blogserv.admindeleteBlog(blogId);
		return ResponseEntity.ok("Blog deleted Successfully");	
	}
	@GetMapping("/deletedblogs")
	public ResponseEntity<List<BlogResponseDTO>> getAllDeletedBlogs(){
		return ResponseEntity.ok(blogserv.getDeletedBlogs());
	}

}
