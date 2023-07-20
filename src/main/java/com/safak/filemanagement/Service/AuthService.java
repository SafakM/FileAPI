package com.safak.filemanagement.Service;

import com.safak.filemanagement.Payload.LoginDto;
import com.safak.filemanagement.Payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
