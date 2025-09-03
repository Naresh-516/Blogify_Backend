package com.nt.service;



import java.util.Optional;

import com.nt.dto.ChangePasswordDTO;
import com.nt.dto.UserLoginDTO;
import com.nt.dto.UserRegisterDTO;
import com.nt.dto.UserResponseDTO;
import com.nt.dto.UserUpdateDTO;
import com.nt.entity.Users;

public interface IUserService {
public String registerUser(UserRegisterDTO dto);
public UserResponseDTO updateUserProfile(String userId, UserUpdateDTO dto);
public UserResponseDTO loginUser(UserLoginDTO dto);
public String changePassword(String userId, ChangePasswordDTO dto);
public String deleteUser(UserLoginDTO dto);
}
