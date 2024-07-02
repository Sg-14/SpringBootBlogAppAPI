package com.springboot.blog.springboot_blog_rest_api.repository;

import com.springboot.blog.springboot_blog_rest_api.entity.Comment;
import com.springboot.blog.springboot_blog_rest_api.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostId(long id, Pageable pageable);
    Comment findByPostIdAndId(@Param("post_id") long postId,@Param("id") long commentId);
}
