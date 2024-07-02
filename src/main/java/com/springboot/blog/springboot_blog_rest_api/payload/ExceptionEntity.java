package com.springboot.blog.springboot_blog_rest_api.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class ExceptionEntity {
    private Date time;
    private String message;
    private String description;
}
