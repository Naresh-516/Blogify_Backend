package com.nt.controller;
import java.util.Optional;

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

import com.nt.dto.ChangePasswordDTO;
import com.nt.dto.UserLoginDTO;
import com.nt.dto.UserRegisterDTO;
import com.nt.dto.UserResponseDTO;
import com.nt.dto.UserUpdateDTO;
import com.nt.entity.Users;
import com.nt.service.IUserService;

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
	@PutMapping("/update-profile/{userId}")
	public ResponseEntity<UserResponseDTO> updateProfile(@PathVariable String userId,
	                                                     @RequestBody UserUpdateDTO dto) {
	    return ResponseEntity.ok(serv.updateUserProfile(userId,dto));
	}
	@PutMapping("/change-password/{userId}")
	public ResponseEntity<String> changePassword(@PathVariable String userId,
	                                             @RequestBody ChangePasswordDTO dto) {
	    return ResponseEntity.ok(serv.changePassword(userId,dto));
	}
	@PostMapping("/delete")
	public ResponseEntity<String> deleteAccount(@RequestBody UserLoginDTO dto){
		return ResponseEntity.ok(serv.deleteUser(dto));
	}
}