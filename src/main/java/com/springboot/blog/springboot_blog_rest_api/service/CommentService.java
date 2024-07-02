package com.springboot.blog.springboot_blog_rest_api.service;

import com.springboot.blog.springboot_blog_rest_api.payload.CommentDto;
import com.springboot.blog.springboot_blog_rest_api.payload.CommentResponse;
import com.springboot.blog.springboot_blog_rest_api.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long postId, CommentDto commentDto);
    public CommentResponse getAllComments(long postId, int pageNo, int pageSize, String sortBy, String sortDir);
    public CommentDto getComment(long postId, long commentId);
    public void deleteComment(long postId, long commentId);
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
}
