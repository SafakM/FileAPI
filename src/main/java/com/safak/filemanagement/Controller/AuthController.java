package com.safak.filemanagement.Controller;

import com.safak.filemanagement.Payload.JWTAuthResponse;
import com.safak.filemanagement.Payload.LoginDto;
import com.safak.filemanagement.Payload.RegisterDto;
import com.safak.filemanagement.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @Operation(summary = "Signin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File Recieved Succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){

            String token= authService.login(loginDto);

            JWTAuthResponse jwtAuthResponse=new JWTAuthResponse();
            jwtAuthResponse.setAccessToken(token);

            return ResponseEntity.ok(jwtAuthResponse);
    }


    @Operation(summary = "Save new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New User Saved Successfuly"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @PostMapping(value = {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response= authService.register(registerDto);
        return ResponseEntity.ok(response);
    }
}
