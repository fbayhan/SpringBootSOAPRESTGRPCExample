package com.fatihbayhan.libraryrest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.fatihbayhan.libraryrest.client.gen.CategoryResponse;
import com.fatihbayhan.libraryrest.client.gen.CreateCategoryRequest;
import com.fatihbayhan.libraryrest.client.gen.DeleteCategoryRequest;
import com.fatihbayhan.libraryrest.client.gen.DeleteCategoryResponse;
import com.fatihbayhan.libraryrest.client.gen.GetAllCategoriesRequest;
import com.fatihbayhan.libraryrest.client.gen.GetAllCategoriesResponse;
import com.fatihbayhan.libraryrest.client.gen.GetCategoryRequest;
import com.fatihbayhan.libraryrest.client.gen.UpdateCategoryRequest;
import com.fatihbayhan.libraryrest.dto.CategoryDTO;

import jakarta.xml.bind.JAXBElement;

@Service
public class CategoryService {
    
    @Autowired
    private WebServiceTemplate webServiceTemplate;
    
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setCategoryName(categoryDTO.getCategoryName());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        CategoryResponse categoryResponse = ((JAXBElement<CategoryResponse>) response).getValue();
        
        CategoryDTO responseDTO = new CategoryDTO();
        responseDTO.setCategoryName(categoryResponse.getCategoryName());
        responseDTO.setCategoryId(Long.parseLong(categoryResponse.getCategoryId()));
        return responseDTO;
    }

    public CategoryDTO getCategory(Long categoryId) {
        GetCategoryRequest request = new GetCategoryRequest();
        request.setCategoryId(categoryId.toString());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        CategoryResponse categoryResponse = ((JAXBElement<CategoryResponse>) response).getValue();
        
        CategoryDTO responseDTO = new CategoryDTO();
        responseDTO.setCategoryName(categoryResponse.getCategoryName());
        responseDTO.setCategoryId(Long.parseLong(categoryResponse.getCategoryId()));
        return responseDTO;
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setCategoryId(categoryDTO.getCategoryId().toString());
        request.setCategoryName(categoryDTO.getCategoryName());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        CategoryResponse categoryResponse = ((JAXBElement<CategoryResponse>) response).getValue();
        
        CategoryDTO responseDTO = new CategoryDTO();
        responseDTO.setCategoryName(categoryResponse.getCategoryName());
        responseDTO.setCategoryId(Long.parseLong(categoryResponse.getCategoryId()));
        return responseDTO;
    }

    public boolean deleteCategory(Long categoryId) {
        DeleteCategoryRequest request = new DeleteCategoryRequest();
        request.setCategoryId(categoryId.toString());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        DeleteCategoryResponse deleteResponse = ((JAXBElement<DeleteCategoryResponse>) response).getValue();
        
        return deleteResponse.isSuccess();
    }

    public List<CategoryDTO> getAllCategories() {
        GetAllCategoriesRequest request = new GetAllCategoriesRequest();
        Object response = webServiceTemplate.marshalSendAndReceive(request);
        GetAllCategoriesResponse categoriesResponse = ((JAXBElement<GetAllCategoriesResponse>) response).getValue();
        
        return categoriesResponse.getCategories().stream()
                .map(category -> {
                    CategoryDTO dto = new CategoryDTO();
                    dto.setCategoryId(Long.parseLong(category.getCategoryId()));
                    dto.setCategoryName(category.getCategoryName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
