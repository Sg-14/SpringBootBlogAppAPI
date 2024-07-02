package com.springboot.blog.springboot_blog_rest_api.service.impl;

import com.springboot.blog.springboot_blog_rest_api.entity.Category;
import com.springboot.blog.springboot_blog_rest_api.exception.BlogAPIException;
import com.springboot.blog.springboot_blog_rest_api.exception.ResourceNotFoundException;
import com.springboot.blog.springboot_blog_rest_api.payload.CategoryDto;
import com.springboot.blog.springboot_blog_rest_api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private com.springboot.blog.springboot_blog_rest_api.repository.CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> collect = categories.stream().map(category -> categoryToDto(category)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = dtoToCategory(categoryDto);
        Category newCategory = categoryRepository.save(category);

        return categoryToDto(newCategory);
    }

    @Override
    public CategoryDto getCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new BlogAPIException(HttpStatus.BAD_REQUEST, "No such category exists")
        );

        return categoryToDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );

        category.setCategoryName(categoryDto.getCategoryName());

        Category save = categoryRepository.save(category);

        return  categoryToDto(save);
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id)
        );

        categoryRepository.delete(category);
    }

    private Category dtoToCategory(CategoryDto categoryDto){
        return mapper.map(categoryDto, Category.class);
    }

    private CategoryDto categoryToDto(Category category){
        return mapper.map(category, CategoryDto.class);
    }
}
