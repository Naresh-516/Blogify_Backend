package com.nt.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.nt.dto.CommentRequestDTO;
import com.nt.dto.CommentResponseDTO;
import com.nt.dto.EditCommentRequestDTO;
import com.nt.entity.Comment;
import com.nt.entity.Users;
import com.nt.service.BlogServiceImpl;

@RestController
@CrossOrigin(origins = {"https://blogifyverse.netlify.app,http://localhost:5173/"}) 
@RequestMapping("/blog")
public class BlogOperationController {
	@Autowired
	private BlogServiceImpl blogserv;
	@PostMapping("/create")
	public ResponseEntity<BlogResponseDTO> postBlog(@RequestBody BlogRequestDTO dto,
			 @AuthenticationPrincipal UserDetails userDetails){
		String email=userDetails.getUsername();
		
	 return ResponseEntity.ok(blogserv.createBlog(dto,email));
	}
	@GetMapping("/getblog/{blogId}")
	public ResponseEntity<BlogResponseDTO> getBlog(@PathVariable String blogId){
		return ResponseEntity.ok(blogserv.getBlogById(blogId));
	}
	@GetMapping("/myblogs")
	public ResponseEntity<List<BlogResponseDTO>> getBlogsByUser(){
		String email = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
		return ResponseEntity.ok(blogserv.getBlogsByUser(email));
	}
	@GetMapping("/allblogs")
	public ResponseEntity<List<BlogResponseDTO>> getAllBlogs(){
		return ResponseEntity.ok(blogserv.getAllBlogs());
	}
	@PutMapping("/update/{blogId}")
	public ResponseEntity<BlogResponseDTO> updateBlog(@PathVariable String blogId,@RequestBody BlogRequestDTO dto,@AuthenticationPrincipal UserDetails userDetails){
		String email=userDetails.getUsername();
		return ResponseEntity.ok(blogserv.updatedBlog(blogId, dto,email));
	}
	@PostMapping("/{blogId}/like-toggle")
	public BlogResponseDTO toggleLike(@PathVariable String blogId,@AuthenticationPrincipal UserDetails userDetails) {
		 String email = userDetails.getUsername();
	    return blogserv.toggleLike(blogId, email);
	}
	@PostMapping("/comment")
	public ResponseEntity<BlogResponseDTO> addComment(@RequestBody CommentRequestDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
	    String email = userDetails.getUsername();
	    	System.out.println(dto);
	    return ResponseEntity.ok( blogserv.addComment(dto.getBlogId(), email, dto.getContent()));
	}
	@PutMapping("/editcomment")
	public ResponseEntity<BlogResponseDTO> editComment(@RequestBody EditCommentRequestDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
	    String email = userDetails.getUsername();
	    	System.out.println(dto);
	    return ResponseEntity.ok( blogserv.editComment(dto.getBlogId(), email, dto.getUpdatedContent(),dto.getCommentId()));
	}
	@DeleteMapping("/deletecomment/{blogId}/{commentId}")
	public ResponseEntity<BlogResponseDTO> deleteComment(@PathVariable String blogId,@PathVariable String commentId, @AuthenticationPrincipal UserDetails userDetails) {
	    String email = userDetails.getUsername();
	    	
	    return ResponseEntity.ok( blogserv.deleteComment(blogId, email,commentId ));
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
	@GetMapping("/admin/userblogs/{UserId}")
	public ResponseEntity<List<BlogResponseDTO>> getBlogsByUser(@PathVariable String UserId,Authentication auth){
		 System.out.println("Logged in user: " + auth.getName());
		    System.out.println("Authorities: " + auth.getAuthorities());
		return ResponseEntity.ok(blogserv.getBlogsByUser02(UserId));
	}

}
