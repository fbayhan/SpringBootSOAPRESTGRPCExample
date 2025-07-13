package com.fatihbayhan.LibraryManagementSystem.endpoint;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

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

import jakarta.xml.bind.JAXBElement;

@Endpoint
public class CategoryEndpoint {
    private static final String NAMESPACE_URI = "http://fatihbayhan.com/librarymanagement";

    @Autowired
    private CategoryService categoryService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateCategoryRequest")
    @ResponsePayload
    public JAXBElement<CategoryResponse> createCategory(@RequestPayload JAXBElement<CreateCategoryRequest> request) {
        CategoryResponse response = categoryService.createCategory(request.getValue());
        return createResponseJaxbElement(response, CategoryResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetCategoryRequest")
    @ResponsePayload
    public JAXBElement<CategoryResponse> getCategory(@RequestPayload JAXBElement<GetCategoryRequest> request) {
        CategoryResponse response = categoryService.getCategory(request.getValue());
        return createResponseJaxbElement(response, CategoryResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllCategoriesRequest")
    @ResponsePayload
    public JAXBElement<GetAllCategoriesResponse> getAllCategories(@RequestPayload JAXBElement<GetAllCategoriesRequest> request) {
        GetAllCategoriesResponse response = categoryService.getAllCategories(request.getValue());
        return createResponseJaxbElement(response, GetAllCategoriesResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateCategoryRequest")
    @ResponsePayload
    public JAXBElement<CategoryResponse> updateCategory(@RequestPayload JAXBElement<UpdateCategoryRequest> request) {
        CategoryResponse response = categoryService.updateCategory(request.getValue());
        return createResponseJaxbElement(response, CategoryResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteCategoryRequest")
    @ResponsePayload
    public JAXBElement<DeleteCategoryResponse> deleteCategory(@RequestPayload JAXBElement<DeleteCategoryRequest> request) {
        DeleteCategoryResponse response = categoryService.deleteCategory(request.getValue());
        return createResponseJaxbElement(response, DeleteCategoryResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchCategoriesRequest")
    @ResponsePayload
    public JAXBElement<SearchCategoriesResponse> searchCategories(@RequestPayload JAXBElement<SearchCategoriesRequest> request) {
        SearchCategoriesResponse response = categoryService.searchCategories(request.getValue());
        return createResponseJaxbElement(response, SearchCategoriesResponse.class);
    }

    private <T> JAXBElement<T> createResponseJaxbElement(T object, Class<T> clazz) {
        return new JAXBElement<>(new QName(NAMESPACE_URI, clazz.getSimpleName()), clazz, object);
    }
}
