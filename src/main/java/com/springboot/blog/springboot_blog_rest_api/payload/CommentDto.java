package com.springboot.blog.springboot_blog_rest_api.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
    private long id;

    @NotEmpty(message = "The name of the person can't be empty")
    private String name;
    @Email(message = "Enter a valid email address")
    private String email;
    @NotEmpty(message = "Empty comment is not allowed")
    private String body;
}
