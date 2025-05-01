package com.nt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.dto.AdminLoginDTO;
import com.nt.dto.AdminResponseDTO;
import com.nt.entity.Admin;
import com.nt.repo.AdminRepository;
@Service("Admin")
public class AdminServiceImpl implements IAdminService{
	@Autowired
	private  AdminRepository adminrepo;
	@Autowired
	private  PasswordEncoder passwordEncoder;

	@Override
	public AdminResponseDTO loginAdmin(AdminLoginDTO dto) {
		Optional<Admin> optadmin=adminrepo.findByEmail(dto.getEmail());
		if(optadmin.isEmpty()) return null;
		Admin admin=optadmin.get();
		
		if(!passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
			return null;
		}
		AdminResponseDTO res=new AdminResponseDTO();
		res.setEmail(admin.getEmail());
		res.setName(admin.getName());
		
		return res;
	}

}
