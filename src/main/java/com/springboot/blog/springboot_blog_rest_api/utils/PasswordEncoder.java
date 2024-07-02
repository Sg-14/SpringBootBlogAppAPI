package com.springboot.blog.springboot_blog_rest_api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("shivank"));
        System.out.println(encoder.encode("admin"));
    }
}
