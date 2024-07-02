package com.springboot.blog.springboot_blog_rest_api.controller;

import com.springboot.blog.springboot_blog_rest_api.payload.AuthResponse;
import com.springboot.blog.springboot_blog_rest_api.payload.LoginDto;
import com.springboot.blog.springboot_blog_rest_api.payload.RegisterDto;
import com.springboot.blog.springboot_blog_rest_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto){
        String response = authService.Login(loginDto);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(response);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.Register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
