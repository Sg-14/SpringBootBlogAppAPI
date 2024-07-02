package com.springboot.blog.springboot_blog_rest_api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private List<CommentDto> content;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private boolean last;
    private long totalContent;
}
