package com.springboot.blog.springboot_blog_rest_api.service;

import com.springboot.blog.springboot_blog_rest_api.payload.LoginDto;
import com.springboot.blog.springboot_blog_rest_api.payload.RegisterDto;

public interface AuthService {
    String Login(LoginDto loginDto);
    String Register(RegisterDto registerDto);
}
