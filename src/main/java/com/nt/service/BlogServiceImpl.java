package com.nt.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.dto.BlogRequestDTO;
import com.nt.dto.BlogResponseDTO;
import com.nt.entity.Blog;
import com.nt.entity.DeletedBlogs;
import com.nt.entity.Users;
import com.nt.repo.BlogRepository;
import com.nt.repo.DeletedBlogRepository;
import com.nt.repo.UserRepository;
@Service("service")
public class BlogServiceImpl implements IBlogService{
    @Autowired
	private BlogRepository blogrepo;
    @Autowired
    private UserRepository userrepo;
    @Autowired
    private DeletedBlogRepository delrepo;

	@Override
	public BlogResponseDTO createBlog(BlogRequestDTO dto) {
		Optional<Users> optuser=userrepo.findById(dto.getUserId());
		if(optuser.isEmpty()) {
			throw new RuntimeException("User Not Found");
		}
		Users user=optuser.get();
		Blog blog=new Blog();
		blog.setTitle(dto.getTitle());
		blog.setContent(dto.getContent());
		blog.setTags(dto.getTags());
		blog.setUser(user);

	        // Set timestamps manually
	        LocalDateTime currentDateTime = LocalDateTime.now();
	        
	        // Set postedAt if it's a new blog (null means it is new)
	        if (blog.getPostedAt() == null) {
	            blog.setPostedAt(currentDateTime);
	        }
	        blog.setUpdatedAt(currentDateTime);
		Blog saved=blogrepo.save(blog);
		Optional<Users> user01=userrepo.findById(dto.getUserId());
		if(user01.isPresent()) {
			Users user11=user01.get();
		user11.getBlogs().add(blog);
		userrepo.save(user11);
		}
		
		return convertToDto(saved);
	}

	@Override
	public List<BlogResponseDTO> getAllBlogs() {
		List<Blog> blogs=blogrepo.findAll();
		return blogs.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<BlogResponseDTO> getBlogsByUser(String userId) {
		Optional<Users> optuser=userrepo.findById(userId);
		if(optuser.isEmpty()) {
			throw new RuntimeException("User Not Found");
		}
		List<Blog> blogs=blogrepo.findByUser(optuser.get());
		return blogs.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	private BlogResponseDTO convertToDto(Blog blog) {
		BlogResponseDTO dto=new BlogResponseDTO();
		dto.setId(blog.getId());
		dto.setTitle(blog.getTitle());
		dto.setContent(blog.getContent());
		dto.setTags(blog.getTags());
		dto.setPostedAt(blog.getPostedAt());
		dto.setUpdatedAt(blog.getUpdatedAt());
		dto.setUserId(blog.getUser().getId());
		dto.setUserName(blog.getUser().getName());
		return dto;
		}
	private BlogResponseDTO convertToDto(DeletedBlogs deletedBlog) {
	    BlogResponseDTO dto = new BlogResponseDTO();
	    dto.setId(deletedBlog.getId());
	    dto.setTitle(deletedBlog.getTitle());
	    dto.setContent(deletedBlog.getContent());
	    dto.setTags(deletedBlog.getTags());
	    dto.setPostedAt(deletedBlog.getCreatedAt()); // mapping createdAt from deleted blog
	    dto.setUpdatedAt(deletedBlog.getDeletedAt()); // mapping deletedAt as updatedAt
	    dto.setUserId(deletedBlog.getUserId());
	    dto.setUserName(deletedBlog.getUserName());
	    return dto;
	}


	@Override
	public BlogResponseDTO updatedBlog(String blogId, BlogRequestDTO dto) {
		Blog blog=blogrepo.findById(blogId).orElseThrow(()->new RuntimeException("Blog not Found"));
		if(!blog.getUser().getId().equals(dto.getUserId())) {
			throw new RuntimeException("Unauthorized to update this Blog");
		}
		blog.setTitle(dto.getTitle());
		blog.setContent(dto.getContent());
		blog.setTags(dto.getTags());
		 LocalDateTime currentDateTime = LocalDateTime.now();
		 blog.setUpdatedAt(currentDateTime);
		Blog updatedblog=blogrepo.save(blog);
	
		return convertToDto(updatedblog);
	}

	@Override
	public void deletedBlog(String blogId) {
		Blog blog=blogrepo.findById(blogId).orElseThrow(()-> new RuntimeException("Blog not Found"));
		blogrepo.delete(blog);
	}

	@Override
	public void admindeleteBlog(String blogId) {
		Blog blog=blogrepo.findById(blogId).orElseThrow(()-> new RuntimeException("Blog not Found"));
		DeletedBlogs del=new DeletedBlogs();
		del.setOriginalId(blog.getId());
		del.setTitle(blog.getTitle());
		del.setContent(blog.getContent());
		del.setTags(blog.getTags());
		del.setUserId(blog.getUser().getId());
		del.setCreatedAt(blog.getPostedAt());
		del.setDeletedAt(LocalDateTime.now());
		del.setUserName(blog.getUser().getName());
		delrepo.save(del);
		blogrepo.delete(blog);
	}

	@Override
	public List<BlogResponseDTO> getDeletedBlogs() {
		List<DeletedBlogs> blogs=delrepo.findAll();
		return blogs.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	

}
