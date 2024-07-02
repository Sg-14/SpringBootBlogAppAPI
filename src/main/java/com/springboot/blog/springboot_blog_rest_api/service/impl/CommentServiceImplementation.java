package com.springboot.blog.springboot_blog_rest_api.service.impl;

import com.springboot.blog.springboot_blog_rest_api.entity.Comment;
import com.springboot.blog.springboot_blog_rest_api.entity.Post;
import com.springboot.blog.springboot_blog_rest_api.exception.BlogAPIException;
import com.springboot.blog.springboot_blog_rest_api.exception.ResourceNotFoundException;
import com.springboot.blog.springboot_blog_rest_api.payload.CommentDto;
import com.springboot.blog.springboot_blog_rest_api.payload.CommentResponse;
import com.springboot.blog.springboot_blog_rest_api.payload.PostDto;
import com.springboot.blog.springboot_blog_rest_api.repository.CommentRepository;
import com.springboot.blog.springboot_blog_rest_api.repository.PostRepository;
import com.springboot.blog.springboot_blog_rest_api.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CommentServiceImplementation implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = dtoToComment(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);

        Comment commentResponse = commentRepository.save(comment);

        return commentToDto(commentResponse);
    }

    @Override
    public CommentResponse getAllComments(long postId, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Comment> page = commentRepository.findByPostId(postId, pageable);
        List<CommentDto> collection = page.stream().map(
                comment -> commentToDto(comment)).collect(Collectors.toList());

        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setContent(collection);
        commentResponse.setPageNo(page.getNumber());
        commentResponse.setPageSize(page.getSize());
        commentResponse.setLast(page.isLast());
        commentResponse.setTotalPages(page.getTotalPages());
        commentResponse.setTotalContent(page.getTotalElements());

        return commentResponse;
    }

    @Override
    public CommentDto getComment (long postId, long commentId) {
        Comment comment = findCommentWithPostAndCommentId(postId, commentId);
        return commentToDto(comment);
    }

    @Override
    public void deleteComment(long postId, long commentId){
        Comment comment = findCommentWithPostAndCommentId(postId, commentId);
        commentRepository.delete(comment);
    }
    
    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto){
        Comment comment = findCommentWithPostAndCommentId(postId, commentId);
        comment.setBody(Objects.equals(commentDto.getBody(), null) ? comment.getBody() : commentDto.getBody());
        comment.setName(Objects.equals(commentDto.getName(), null) ? comment.getName() : commentDto.getName());
        comment.setEmail(Objects.equals(commentDto.getEmail(), null) ? comment.getEmail() : commentDto.getEmail());

        Comment newComment = commentRepository.save(comment);

        return commentToDto(newComment);
    }

    private Comment dtoToComment(CommentDto commentDto){
        return mapper.map(commentDto, Comment.class);
    }

    private CommentDto commentToDto(Comment comment){
        return mapper.map(comment, CommentDto.class);
    }

    private Comment findCommentWithPostAndCommentId(long postId, long commentId){
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId);
        if(comment == null){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment for given post does not exist!!");
        }
        return comment;
    }
}
