package com.springboot.blog.springboot_blog_rest_api.service;

import com.springboot.blog.springboot_blog_rest_api.payload.PostDto;
import com.springboot.blog.springboot_blog_rest_api.payload.PostResponse;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    public PostDto getPost(long id);
    public void deletePost(long id);
    public PostDto updatePost(PostDto postDto,long id);
    public List<PostDto> getPostsByCategory(long categoryId);
}
