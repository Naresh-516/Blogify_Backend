package com.nt.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.dto.BlogRequestDTO;
import com.nt.dto.BlogResponseDTO;
import com.nt.entity.Blog;
import com.nt.entity.Comment;
import com.nt.entity.DeletedBlogs;
import com.nt.entity.Users;
import com.nt.exception.BlogNotFoundException;
import com.nt.exception.UserNotFoundException;
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
	public BlogResponseDTO createBlog(BlogRequestDTO dto,String email) {
		Optional<Users> optuser=userrepo.findByEmail(email);
		if(optuser.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		Users user=optuser.get();
		Blog blog=new Blog();
		blog.setTitle(dto.getTitle());
		blog.setContent(dto.getContent());
		blog.setTags(dto.getTags());
		blog.setUserid(user.getId());
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    if (blog.getPostedAt() == null) {
	       blog.setPostedAt(currentDateTime);
	    }
	    blog.setUpdatedAt(currentDateTime);
		Blog saved=blogrepo.save(blog);
		return convertToDto(saved);
	}

	@Override
	public List<BlogResponseDTO> getAllBlogs() {
     List<Blog> blogs = blogrepo.findAll();
	    return blogs.stream()
	                .map(blog -> convertToDto(blog))
	                .collect(Collectors.toList());
	}

	@Override
	public List<BlogResponseDTO> getBlogsByUser(String email) {
	    // Fetch current user by email
	    Users currentUser = userrepo.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("User not found"));

	    String currentUserId = currentUser.getId();

	    // Get blogs of this user
	    List<Blog> blogs = blogrepo.findByUserid(currentUserId);

	    // Convert to DTO passing currentUserId
	    return blogs.stream()
	            .map(blog -> convertToDto(blog))
	            .collect(Collectors.toList());
	}

	@Override
	public BlogResponseDTO updatedBlog(String blogId, BlogRequestDTO dto, String email) {
	    Blog blog = blogrepo.findById(blogId)
	            .orElseThrow(() -> new BlogNotFoundException("Blog not Found"));

	    // fetch logged-in user
	    Users loggedInUser = userrepo.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("User not found"));

	    // check ownership
	    if (!blog.getUserid().equals(loggedInUser.getId())) {
	        throw new RuntimeException("Unauthorized to update this Blog");
	    }

	    blog.setTitle(dto.getTitle());
	    blog.setContent(dto.getContent());
	    blog.setTags(dto.getTags());
	    blog.setUpdatedAt(LocalDateTime.now());

	    Blog updatedblog = blogrepo.save(blog);
	    return convertToDto(updatedblog);
	}

	@Override
	public void deletedBlog(String blogId) {
		Blog blog=blogrepo.findById(blogId).orElseThrow(()-> new BlogNotFoundException("Blog not Found"));
		blogrepo.delete(blog);
	}

	@Override
	public void admindeleteBlog(String blogId) {
		Blog blog=blogrepo.findById(blogId).orElseThrow(()-> new BlogNotFoundException("Blog not Found"));
		DeletedBlogs del=new DeletedBlogs();
		del.setOriginalId(blog.getId());
		del.setTitle(blog.getTitle());
		del.setContent(blog.getContent());
		del.setTags(blog.getTags());
		del.setUserId(blog.getUserid());
		del.setCreatedAt(blog.getPostedAt());
		del.setDeletedAt(LocalDateTime.now());
		del.setUserName(userrepo.findById(blog.getUserid()).get().getName());
		delrepo.save(del);
		blogrepo.delete(blog);
	
	}

	@Override
	public List<BlogResponseDTO> getDeletedBlogs() {
		List<DeletedBlogs> blogs=delrepo.findAll();
		return blogs.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<BlogResponseDTO> getBlogsByUser02(String UserId) {
		Optional<Users> optuser=userrepo.findById(UserId);
		if(optuser.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		List<Blog> blogs=blogrepo.findByUserid(optuser.get().getId());
		return blogs.stream().map(blog->convertToDto(blog)).collect(Collectors.toList());
		
	}
	@Override
	public BlogResponseDTO toggleLike(String blogId, String email) {
	    Blog blog = blogrepo.findById(blogId)
	        .orElseThrow(() -> new BlogNotFoundException("Blog not found"));

	    Users user = userrepo.findByEmail(email)
	        .orElseThrow(() -> new UserNotFoundException("User not found"));

	    if (blog.getLikedUsers() == null) {
	        blog.setLikedUsers(new HashSet<>());
	    }

	    Set<String> likedUsers = blog.getLikedUsers();

	    if (likedUsers.contains(user.getId())) {
	        // User already liked the blog → unlike it
	        likedUsers.remove(user.getId());
	    } else {
	        // User hasn't liked it → like it
	        likedUsers.add(user.getId());
	    }

	    blog.setLikeCount(likedUsers.size());

	    blogrepo.save(blog);

	    return convertToDto(blog);
	}
	public BlogResponseDTO addComment(String blogId, String email, String content) {
	    Blog blog = blogrepo.findById(blogId)
	        .orElseThrow(() -> new BlogNotFoundException("Blog not Found"));
	    Users user = userrepo.findByEmail(email)
	        .orElseThrow(() -> new UserNotFoundException("User not found"));

	    if(blog.getComments() == null) {
	        blog.setComments(new ArrayList<>());
	    }
	    System.out.println("Adding comment for blogId=" + blogId + " by user=" + email);

	    Comment comment = new Comment();
	    comment.setCommentId(UUID.randomUUID().toString()); // Generate ID if needed
	    comment.setBlogId(blogId);
	    comment.setUserId(user.getId());
	    comment.setUsername(user.getName());
	    comment.setContent(content);
	    comment.setTimestamp(new Date());

	    blog.getComments().add(comment);

	    blogrepo.save(blog); // Save only the blog with embedded comment

	    return convertToDto(blog);
	}

	public BlogResponseDTO getBlogById(String blogId) {
	    return blogrepo.findById(blogId)
	                   .map(blog -> convertToDto(blog))  
	                   .orElseThrow(() -> new BlogNotFoundException("Blog not found with id: " + blogId));
	}

	@Override
	public BlogResponseDTO editComment(String blogId, String email, String content, String commentId) {
	    Blog blog = blogrepo.findById(blogId)
	            .orElseThrow(() -> new BlogNotFoundException("Blog not found"));

	    Users user = userrepo.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("User not found"));

	    Comment comment = blog.getComments().stream()
	            .filter(c -> c.getCommentId().equals(commentId))
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Comment not found"));

	    
	    if (!comment.getUserId().equals(user.getId())) {
	        throw new RuntimeException("You are not authorized to edit this comment");
	    }

	    comment.setContent(content);
	    comment.setTimestamp(new Date());

	    blogrepo.save(blog);
	    return convertToDto(blog);
	}
	@Override
	public BlogResponseDTO deleteComment(String blogId, String email, String commentId) {
	    
	    Blog blog = blogrepo.findById(blogId)
	            .orElseThrow(() -> new BlogNotFoundException("Blog not found"));

	    Users user = userrepo.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("User not found"));

	    Comment comment = blog.getComments().stream()
	            .filter(c -> c.getCommentId().equals(commentId))
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Comment not found"));
	    if (!comment.getUserId().equals(user.getId())) {
	        throw new RuntimeException("You are not authorized to delete this comment");
	    }

	    blog.getComments().remove(comment);

	    blogrepo.save(blog);
	    return convertToDto(blog);
	}
	private BlogResponseDTO convertToDto(Blog blog) {
		BlogResponseDTO dto=new BlogResponseDTO();
		dto.setId(blog.getId());
		dto.setTitle(blog.getTitle());
		dto.setContent(blog.getContent());
		dto.setTags(blog.getTags());
		dto.setPostedAt(blog.getPostedAt());
		dto.setUpdatedAt(blog.getUpdatedAt());
		dto.setUserId(blog.getUserid());
		dto.setUserName(userrepo.findById(blog.getUserid()).get().getName());
		dto.setLikeCount(blog.getLikedUsers() != null ? blog.getLikedUsers().size() : 0);
		dto.setLikedUsers(blog.getLikedUsers());
		dto.setComments(blog.getComments());
		return dto;
		}
	private BlogResponseDTO convertToDto(DeletedBlogs deletedBlog) {
	    BlogResponseDTO dto = new BlogResponseDTO();
	    dto.setId(deletedBlog.getId());
	    dto.setTitle(deletedBlog.getTitle());
	    dto.setContent(deletedBlog.getContent());
	    dto.setTags(deletedBlog.getTags());
	    dto.setPostedAt(deletedBlog.getCreatedAt()); 
	    dto.setUpdatedAt(deletedBlog.getDeletedAt()); 
	    dto.setUserId(deletedBlog.getUserId());
	    dto.setUserName(deletedBlog.getUserName());
	    return dto;
	}


	

}
