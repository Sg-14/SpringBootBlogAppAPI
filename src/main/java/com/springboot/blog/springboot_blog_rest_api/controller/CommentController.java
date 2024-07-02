package com.springboot.blog.springboot_blog_rest_api.controller;

import com.springboot.blog.springboot_blog_rest_api.payload.CommentDto;
import com.springboot.blog.springboot_blog_rest_api.payload.CommentResponse;
import com.springboot.blog.springboot_blog_rest_api.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("{postId}/comments")
    public ResponseEntity<CommentResponse> getAllComments(
            @PathVariable long postId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
            ){
        return ResponseEntity.ok(commentService.getAllComments(postId, pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable long postId, @PathVariable long commentId){
        return ResponseEntity.ok(commentService.getComment(postId, commentId));
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long postId, @PathVariable long commentId){
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment Deleted Successfully");
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable long postId, @PathVariable long commentId,
            @Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }
}
