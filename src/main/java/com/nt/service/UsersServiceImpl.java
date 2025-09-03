package com.nt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.dto.ChangePasswordDTO;
import com.nt.dto.UserLoginDTO;
import com.nt.dto.UserRegisterDTO;
import com.nt.dto.UserResponseDTO;
import com.nt.dto.UserUpdateDTO;
import com.nt.entity.Users;
import com.nt.exception.EmailAlreadyExistsException;
import com.nt.exception.PasswordMismatchException;
import com.nt.exception.UserNotFoundException;
import com.nt.repo.BlogRepository;
import com.nt.repo.UserRepository;
import com.nt.security.JwtUtil;
@Service
public class UsersServiceImpl implements IUserService{

	@Autowired
	private UserRepository userrepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private BlogRepository blogrepo;

	@Override
	public String registerUser(UserRegisterDTO dto) {
		if(userrepo.findByEmail(dto.getEmail().toLowerCase()).isPresent()) {
			throw new EmailAlreadyExistsException("Email already registered: " + dto.getEmail());
		}
		Users user=new Users();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail().toLowerCase());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setAddress(dto.getAddress());
		user.setGender(dto.getGender());
		user.setMobile(dto.getMobile());
		userrepo.save(user);
		return "User Registered Successfully";
	}


	@Override
	public UserResponseDTO loginUser(UserLoginDTO request) {
//		Optional<Users> optionaluser=userrepo.findByEmail(dto.getEmail().toLowerCase());
//		if(optionaluser.isEmpty()) return null;
//		Users user=optionaluser.get();
//		if(!passwordEncoder.matches(dto.getPassword(),user.getPassword())) {
//			return null;
//		}
//		UserResponseDTO res=new UserResponseDTO();
//		res.setId(user.getId());
//		res.setName(user.getName());
//		res.setEmail(user.getEmail());
//		res.setGender(user.getGender());
//		res.setMobile(user.getMobile());
//		res.setAddress(user.getAddress());
//		return res;
		try {
    	    authenticationManager.authenticate(
    	        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    	    );
    	} catch (Exception e) {
    	    System.out.println("Login failed: " + e.getMessage());
    	    throw e;
    	}
        Users user = userrepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String token = jwtutil.generateToken(user);
        UserResponseDTO res=new UserResponseDTO();
 		res.setId(user.getId());
		res.setName(user.getName());
		res.setEmail(user.getEmail());
		res.setGender(user.getGender());
		res.setMobile(user.getMobile());
		res.setAddress(user.getAddress());
		res.setToken(token);
		return res;

       // return new AuthResponse(token, user.getMail(), user.getRole());
	}


	@Override
	public UserResponseDTO updateUserProfile(String email, UserUpdateDTO dto) {
		Users user = userrepo.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("User not found"));

	    user.setName(dto.getName());
	    user.setAddress(dto.getAddress());
	    user.setGender(dto.getGender());
	    user.setMobile(dto.getMobile());

	    userrepo.save(user);

	    UserResponseDTO response = new UserResponseDTO();
	    response.setId(user.getId());
	    response.setName(user.getName());
	    response.setEmail(user.getEmail());
	    response.setAddress(user.getAddress());
	    response.setGender(user.getGender());
	    response.setMobile(user.getMobile());
	    return response;

	}


	@Override
	public String changePassword(String email, ChangePasswordDTO dto) {
		Users user = userrepo.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("User not found"));

	    if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
	        throw new PasswordMismatchException("Old password is incorrect");
	    }

	    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
	    userrepo.save(user);

	    return "Password changed successfully";

	}

	@Override
	public String deleteUser(UserLoginDTO dto) {
		Optional<Users> optionaluser=userrepo.findByEmail(dto.getEmail());
		if(optionaluser.isEmpty()) return "Invalid EmailID";
		Users user=optionaluser.get();
		if(!passwordEncoder.matches(dto.getPassword(),user.getPassword())) {
			throw new PasswordMismatchException("password is incorrect");
		}
		
		 blogrepo.deleteAll(blogrepo.findByUserid(user.getId()));
		
		userrepo.delete(user);
		
		return "Account deleted Successfully";
	}
	

}
