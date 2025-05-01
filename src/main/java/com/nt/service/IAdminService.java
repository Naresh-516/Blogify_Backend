package com.nt.service;

import com.nt.dto.AdminLoginDTO;
import com.nt.dto.AdminResponseDTO;

public interface IAdminService {
	public AdminResponseDTO loginAdmin(AdminLoginDTO dto);


}