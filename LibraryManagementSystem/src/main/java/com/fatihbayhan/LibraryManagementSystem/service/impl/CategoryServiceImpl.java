package com.fatihbayhan.LibraryManagementSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fatihbayhan.LibraryManagementSystem.model.Category;
import com.fatihbayhan.LibraryManagementSystem.repository.CategoryRepository;
import com.fatihbayhan.LibraryManagementSystem.service.CategoryService;
import com.fatihbayhan.librarymanagement.CategoryResponse;
import com.fatihbayhan.librarymanagement.CreateCategoryRequest;
import com.fatihbayhan.librarymanagement.DeleteCategoryRequest;
import com.fatihbayhan.librarymanagement.DeleteCategoryResponse;
import com.fatihbayhan.librarymanagement.GetAllCategoriesRequest;
import com.fatihbayhan.librarymanagement.GetAllCategoriesResponse;
import com.fatihbayhan.librarymanagement.GetCategoryRequest;
import com.fatihbayhan.librarymanagement.SearchCategoriesRequest;
import com.fatihbayhan.librarymanagement.SearchCategoriesResponse;
import com.fatihbayhan.librarymanagement.UpdateCategoryRequest;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        
        Category savedCategory = categoryRepository.save(category);
        
        CategoryResponse response = new CategoryResponse();
        response.setCategoryId(String.valueOf(savedCategory.getId()));
        response.setCategoryName(savedCategory.getCategoryName());
        return response;
    }

    @Override
    public CategoryResponse getCategory(GetCategoryRequest request) {
        Long categoryId = Long.parseLong(request.getCategoryId());
        Category category = categoryRepository.findById(categoryId).orElse(null);
        
        CategoryResponse response = new CategoryResponse();
        if (category != null) {
            response.setCategoryId(String.valueOf(category.getId()));
            response.setCategoryName(category.getCategoryName());
        }
        return response;
    }

    @Override
    public GetAllCategoriesResponse getAllCategories(GetAllCategoriesRequest request) {
        List<Category> categories = categoryRepository.findAll();
        
        GetAllCategoriesResponse response = new GetAllCategoriesResponse();
        for (Category category : categories) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryId(String.valueOf(category.getId()));
            categoryResponse.setCategoryName(category.getCategoryName());
            response.getCategories().add(categoryResponse);
        }
        return response;
    }

    @Override
    public CategoryResponse updateCategory(UpdateCategoryRequest request) {
        Category category = new Category();
        category.setId(Long.parseLong(request.getCategoryId()));
        category.setCategoryName(request.getCategoryName());
        
        Category updatedCategory = null;
        if (categoryRepository.existsById(category.getId())) {
            updatedCategory = categoryRepository.save(category);
        }
        
        CategoryResponse response = new CategoryResponse();
        if (updatedCategory != null) {
            response.setCategoryId(String.valueOf(updatedCategory.getId()));
            response.setCategoryName(updatedCategory.getCategoryName());
        }
        return response;
    }

    @Override
    public DeleteCategoryResponse deleteCategory(DeleteCategoryRequest request) {
        Long categoryId = Long.parseLong(request.getCategoryId());
        categoryRepository.deleteById(categoryId);
        
        DeleteCategoryResponse response = new DeleteCategoryResponse();
        response.setSuccess(true);
        return response;
    }

    @Override
    public SearchCategoriesResponse searchCategories(SearchCategoriesRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Category> categoryPage = categoryRepository.searchCategories(request.getSearchTerm(), pageable);
        
        SearchCategoriesResponse response = new SearchCategoriesResponse();
        for (Category category : categoryPage.getContent()) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setCategoryId(String.valueOf(category.getId()));
            categoryResponse.setCategoryName(category.getCategoryName());
            response.getCategories().add(categoryResponse);
        }
        
        response.setTotalElements(categoryPage.getTotalElements());
        response.setTotalPages(categoryPage.getTotalPages());
        response.setCurrentPage(categoryPage.getNumber());
        
        return response;
    }
}
