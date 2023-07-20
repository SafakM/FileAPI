package com.safak.filemanagement.Service;

import com.safak.filemanagement.Payload.RegisterDto;

import java.util.List;

public interface UserService {
    String createUser(RegisterDto registerDto);

    RegisterDto updateUser(String userId,RegisterDto registerDto);

    RegisterDto updateUserRole(String userId,long userRoleNo);

    RegisterDto findUserById(String userId);

    List<RegisterDto> getAllUsers();
    String deleteUser(String userId);

    RegisterDto findByUsername(String username);
}
