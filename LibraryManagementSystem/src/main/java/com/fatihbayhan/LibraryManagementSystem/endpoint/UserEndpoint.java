package com.fatihbayhan.LibraryManagementSystem.endpoint;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fatihbayhan.LibraryManagementSystem.service.UserService;
import com.fatihbayhan.librarymanagement.CreateUserRequest;
import com.fatihbayhan.librarymanagement.CreateUserResponse;
import com.fatihbayhan.librarymanagement.GetUserRequest;
import com.fatihbayhan.librarymanagement.GetUserResponse;
import com.fatihbayhan.librarymanagement.GetAllUsersRequest;
import com.fatihbayhan.librarymanagement.GetAllUsersResponse;
import com.fatihbayhan.librarymanagement.UpdateUserRequest;
import com.fatihbayhan.librarymanagement.UpdateUserResponse;
import com.fatihbayhan.librarymanagement.DeleteUserRequest;
import com.fatihbayhan.librarymanagement.DeleteUserResponse;
import com.fatihbayhan.librarymanagement.SearchUsersRequest;
import com.fatihbayhan.librarymanagement.SearchUsersResponse;

import jakarta.xml.bind.JAXBElement;

@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://fatihbayhan.com/librarymanagement";

    @Autowired
    private UserService userService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateUserRequest")
    @ResponsePayload
    public JAXBElement<CreateUserResponse> createUser(@RequestPayload JAXBElement<CreateUserRequest> request) {
        CreateUserResponse response = userService.createUser(request.getValue());
        return createResponseJaxbElement(response, CreateUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRequest")
    @ResponsePayload
    public JAXBElement<GetUserResponse> getUser(@RequestPayload JAXBElement<GetUserRequest> request) {
        GetUserResponse response = userService.getUser(request.getValue());
        return createResponseJaxbElement(response, GetUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUsersRequest")
    @ResponsePayload
    public JAXBElement<GetAllUsersResponse> getAllUsers(@RequestPayload JAXBElement<GetAllUsersRequest> request) {
        GetAllUsersResponse response = userService.getAllUsers(request.getValue());
        return createResponseJaxbElement(response, GetAllUsersResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserRequest")
    @ResponsePayload
    public JAXBElement<UpdateUserResponse> updateUser(@RequestPayload JAXBElement<UpdateUserRequest> request) {
        UpdateUserResponse response = userService.updateUser(request.getValue());
        return createResponseJaxbElement(response, UpdateUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteUserRequest")
    @ResponsePayload
    public JAXBElement<DeleteUserResponse> deleteUser(@RequestPayload JAXBElement<DeleteUserRequest> request) {
        DeleteUserResponse response = userService.deleteUser(request.getValue());
        return createResponseJaxbElement(response, DeleteUserResponse.class);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchUsersRequest")
    @ResponsePayload
    public JAXBElement<SearchUsersResponse> searchUsers(@RequestPayload JAXBElement<SearchUsersRequest> request) {
        SearchUsersResponse response = userService.searchUsers(request.getValue());
        return createResponseJaxbElement(response, SearchUsersResponse.class);
    }

    private <T> JAXBElement<T> createResponseJaxbElement(T object, Class<T> clazz) {
        return new JAXBElement<>(new QName(NAMESPACE_URI, clazz.getSimpleName()), clazz, object);
    }
}
