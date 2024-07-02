package com.springboot.blog.springboot_blog_rest_api.service.impl;


import com.springboot.blog.springboot_blog_rest_api.entity.Role;
import com.springboot.blog.springboot_blog_rest_api.entity.User;
import com.springboot.blog.springboot_blog_rest_api.exception.BlogAPIException;
import com.springboot.blog.springboot_blog_rest_api.payload.LoginDto;
import com.springboot.blog.springboot_blog_rest_api.payload.RegisterDto;
import com.springboot.blog.springboot_blog_rest_api.repository.RoleRepository;
import com.springboot.blog.springboot_blog_rest_api.repository.UserRepository;
import com.springboot.blog.springboot_blog_rest_api.security.JwtTokenProvider;
import com.springboot.blog.springboot_blog_rest_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImplementation implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//    public AuthServiceImplementation(AuthenticationManager authenticationManager){
//        this.authenticationManager = authenticationManager;
//    }

    @Override
    public String Login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.
                authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtTokenProvider.generateToken(authenticate);
        return token;
    }

    @Override
    public String Register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "User with this email already exists");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Set<Role> roles = new HashSet<>();
        Role user1 = roleRepository.findByName("ROLE_USER").get();
        roles.add(user1);

        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully";
    }
}
