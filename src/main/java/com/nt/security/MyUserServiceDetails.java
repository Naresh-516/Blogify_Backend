package com.nt.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.nt.entity.Admin;
import com.nt.entity.Users;
import com.nt.repo.AdminRepository;
import com.nt.repo.UserRepository;
@Component
public class MyUserServiceDetails implements UserDetailsService{
	
	 @Autowired
     private UserRepository userrepo;
	 @Autowired
	 private AdminRepository adminrepo;

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    	 Optional<Users> userOpt = userrepo.findByEmail(email);
	         if (userOpt.isPresent()) {
	             return userOpt.get(); // User implements UserDetails
	         }

	         // Check Admin table
	         Optional<Admin> adminOpt = adminrepo.findByEmail(email);
	         if (adminOpt.isPresent()) {
	             return adminOpt.get(); // Admin implements UserDetails
	         }

	         throw new UsernameNotFoundException("User or Admin not found with email: " + email);
	    }

}
