package com.fatihbayhan.LibraryManagementSystem.service;

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

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest request);
    CategoryResponse getCategory(GetCategoryRequest request);
    GetAllCategoriesResponse getAllCategories(GetAllCategoriesRequest request);
    CategoryResponse updateCategory(UpdateCategoryRequest request);
    DeleteCategoryResponse deleteCategory(DeleteCategoryRequest request);
    SearchCategoriesResponse searchCategories(SearchCategoriesRequest request);
}
