package com.nt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.dto.AdminLoginDTO;
import com.nt.dto.AdminResponseDTO;
import com.nt.entity.Admin;
import com.nt.exception.InvalidCredentialsException;
import com.nt.repo.AdminRepository;
import com.nt.security.JwtUtil;
@Service("Admin")
public class AdminServiceImpl implements IAdminService{
	@Autowired
	private  AdminRepository adminrepo;
	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private AuthenticationManager authenticationManager;


	@Override
	public AdminResponseDTO loginAdmin(AdminLoginDTO request) {
		
		try {
    	    authenticationManager.authenticate(
    	        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    	    );
    	} catch (InvalidCredentialsException e) {
    	    System.out.println("Login failed: " + e.getMessage());
    	    throw e;
    	}
		Optional<Admin> optadmin=adminrepo.findByEmail(request.getEmail());
		if(optadmin.isEmpty()) return null;
		Admin admin=optadmin.get();
		
		if(!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
			return null;
		}
		String token = jwtutil.generateToken(admin);
		AdminResponseDTO res=new AdminResponseDTO();
		res.setEmail(admin.getEmail());
		res.setName(admin.getName());
		res.setToken(token);
		
		return res;
	}

}
