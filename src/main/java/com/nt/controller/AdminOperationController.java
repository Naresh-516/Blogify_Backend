package com.nt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nt.dto.AdminLoginDTO;
import com.nt.dto.AdminResponseDTO;
import com.nt.dto.UserWithBlogDTO;
import com.nt.entity.Users;
import com.nt.repo.UserRepository;
import com.nt.service.AdminServiceImpl;
import com.nt.service.BlogServiceImpl;

@RestController
@RequestMapping("/admin")
public class AdminOperationController {

    @Autowired
    private AdminServiceImpl serv;

    @Autowired
    private BlogServiceImpl blogserv;

    @Autowired
    private UserRepository userrepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginDTO dto) {
        AdminResponseDTO res = serv.loginAdmin(dto);
        if (res == null) {
            return ResponseEntity.status(401).body("Invalid admin Credentials");
        }
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity<String> deleteBlogByAdmin(@PathVariable String blogId) { 
        // MongoDB ID is String type now
        blogserv.admindeleteBlog(blogId);
        return ResponseEntity.ok("Blog Deleted Successfully");
    }

    @GetMapping("/all-users-with-blogs")
    public ResponseEntity<List<UserWithBlogDTO>> getAllUsersWithBlogs() {
        List<Users> users = userrepo.findAll();
               
            List<UserWithBlogDTO> result = users.stream().map(user -> {
            UserWithBlogDTO dto = new UserWithBlogDTO();
            dto.setUserId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setMobile(user.getMobile());
            dto.setAddress(user.getAddress());
            dto.setBlogs(user.getBlogs()); // This will automatically populate because of @DBRef
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
