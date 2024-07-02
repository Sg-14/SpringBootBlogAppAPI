package com.springboot.blog.springboot_blog_rest_api.service;


import com.springboot.blog.springboot_blog_rest_api.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategory(long id);
    CategoryDto updateCategory(CategoryDto categoryDto, long id);
    void deleteCategory(long id);
}
