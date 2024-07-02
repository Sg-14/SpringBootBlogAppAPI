package com.springboot.blog.springboot_blog_rest_api.service.impl;

import com.springboot.blog.springboot_blog_rest_api.entity.Category;
import com.springboot.blog.springboot_blog_rest_api.entity.Post;
import com.springboot.blog.springboot_blog_rest_api.exception.ResourceNotFoundException;
import com.springboot.blog.springboot_blog_rest_api.payload.PostDto;
import com.springboot.blog.springboot_blog_rest_api.payload.PostResponse;
import com.springboot.blog.springboot_blog_rest_api.repository.CategoryRepository;
import com.springboot.blog.springboot_blog_rest_api.repository.PostRepository;
import com.springboot.blog.springboot_blog_rest_api.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto){
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
        );
        Post post = dtoToPost(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        PostDto postResponse = postToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> page = postRepository.findAll(pageable);

        // get content for page
        List<Post> posts = page.getContent();

        List<PostDto> postDtoStream = posts.stream().map(post -> postToDto(post)).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtoStream);
        postResponse.setPageNo(page.getNumber());
        postResponse.setPageSize(page.getSize());
        postResponse.setTotalPages(page.getTotalPages());
        postResponse.setLast(page.isLast());
        postResponse.setTotalContent(page.getTotalElements());


        return postResponse;
    }

    @Override
    public PostDto getPost(long id){
        Post byId = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
        PostDto postDto = postToDto(byId);
        return postDto;
    }

    @Override
    public void deletePost(long id){
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Delete", "id", id));
        postRepository.delete(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id){
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Update", "id", id));
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return postToDto(updatedPost);
    }

    @Override
    public List<PostDto> getPostsByCategory(long categoryId) {

        categoryRepository.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category", "id", categoryId)
        );
        List<Post> byCategoryId = postRepository.findByCategoryId(categoryId);
        return byCategoryId.stream().map(post -> postToDto(post)).toList();
    }

    private Post dtoToPost(PostDto postDto){
//        Post post = new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        Post post = mapper.map(postDto, Post.class);
        return post;
    }

    private PostDto postToDto(Post post){
//        PostDto postResponse = new PostDto();
//        postResponse.setId(post.getId());
//        postResponse.setTitle(post.getTitle());
//        postResponse.setDescription(post.getDescription());
//        postResponse.setContent(post.getContent());

        PostDto postResponse = mapper.map(post, PostDto.class);
        return postResponse;
    }
}
