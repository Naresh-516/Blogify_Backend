package com.nt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.dto.ChangePasswordDTO;
import com.nt.dto.UserLoginDTO;
import com.nt.dto.UserRegisterDTO;
import com.nt.dto.UserResponseDTO;
import com.nt.dto.UserUpdateDTO;
import com.nt.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "https://blogifyverse.netlify.app") 
public class UserOperationController {
	@Autowired
	private IUserService serv;
	
	
	@PostMapping("/register")
	public ResponseEntity<String> createUser(@RequestBody UserRegisterDTO dto) {
		String result=serv.registerUser(dto);
		return ResponseEntity.ok(result);
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginDTO dto){
		UserResponseDTO user=serv.loginUser(dto);
		if(user==null) {
			return ResponseEntity.status(401).body("Invalid credentials/UserNotFound");
		}
		return ResponseEntity.ok(user);
	}
	@PutMapping("/update-profile")
	public ResponseEntity<UserResponseDTO> updateProfile(
	                                                     @RequestBody UserUpdateDTO dto ) {
		 String email = ((UserDetails) SecurityContextHolder.getContext()
                 .getAuthentication().getPrincipal()).getUsername();
	    return ResponseEntity.ok(serv.updateUserProfile(email,dto));
	}
	@PutMapping("/change-password")
	@Operation(
		    summary = "Update user profile",
		    description = "Update user profile details by userId",
		    security = @SecurityRequirement(name = "bearerAuth")
		)
	public ResponseEntity<String> changePassword(
	                                             @RequestBody ChangePasswordDTO dto) {
		String email = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
		System.out.println(email);	    
		return ResponseEntity.ok(serv.changePassword(email,dto));
	}
	@PostMapping("/delete")
	@Operation(
		    summary = "Update user profile",
		    description = "Update user profile details by userId",
		    security = @SecurityRequirement(name = "bearerAuth")
		)
	public ResponseEntity<String> deleteAccount(@RequestBody UserLoginDTO dto){
		return ResponseEntity.ok(serv.deleteUser(dto));
	}
}