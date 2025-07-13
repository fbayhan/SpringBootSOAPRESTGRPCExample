package com.fatihbayhan.LibraryManagementSystem.service.impl;

import java.util.List;

import com.fatihbayhan.librarymanagement.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fatihbayhan.LibraryManagementSystem.model.User;
import com.fatihbayhan.LibraryManagementSystem.repository.UserRepository;
import com.fatihbayhan.LibraryManagementSystem.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = new User();
        user.setIdentityNumber(request.getIdentityNumber());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);

        CreateUserResponse response = new CreateUserResponse();
        response.setUserId(savedUser.getId().toString());
        response.setIdentityNumber(savedUser.getIdentityNumber());
        response.setFullName(savedUser.getFullName());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        return response;
    }

    @Override
    public GetUserResponse getUser(GetUserRequest request) {
        Long userId = Long.parseLong(request.getUserId());
        User user = userRepository.findById(userId).orElse(null);

        GetUserResponse response = new GetUserResponse();
        if (user != null) {
            response.setUserId(String.valueOf(user.getId()));
            response.setIdentityNumber(user.getIdentityNumber());
            response.setFullName(user.getFullName());
            response.setEmail(user.getEmail());
        }
        return response;
    }

    @Override
    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) {
        List<User> users = userRepository.findAll();

        GetAllUsersResponse response = new GetAllUsersResponse();
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(String.valueOf(user.getId()));
            userResponse.setIdentityNumber(user.getIdentityNumber());
            userResponse.setFullName(user.getFullName());
            userResponse.setEmail(user.getEmail());
            response.getUsers().add(userResponse);
        }
        return response;
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        User user = new User();
        user.setId(Long.parseLong(request.getUserId()));
        user.setIdentityNumber(request.getIdentityNumber());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
         user.setPassword("Buna gerek yok bence sileyim");

        User updatedUser = null;
        if (userRepository.existsById(user.getId())) {
            updatedUser = userRepository.save(user);
        }

        UpdateUserResponse response = new UpdateUserResponse();
        if (updatedUser != null) {
            response.setUserId(String.valueOf(updatedUser.getId()));
            response.setIdentityNumber(updatedUser.getIdentityNumber());
            response.setFullName(updatedUser.getFullName());
            response.setEmail(updatedUser.getEmail());
        }
        return response;
    }

    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest request) {
        Long userId = Long.parseLong(request.getUserId());
        userRepository.deleteById(userId);

        DeleteUserResponse response = new DeleteUserResponse();
        response.setSuccess(true);
        return response;
    }

    @Override
    public SearchUsersResponse searchUsers(SearchUsersRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<User> userPage = userRepository.searchUsers(request.getSearchTerm(), pageable);

        SearchUsersResponse response = new SearchUsersResponse();
        for (User user : userPage.getContent()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(String.valueOf(user.getId()));
            userResponse.setIdentityNumber(user.getIdentityNumber());
            userResponse.setFullName(user.getFullName());
            userResponse.setEmail(user.getEmail());
            response.getUsers().add(userResponse);
        }

        response.setTotalElements(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());
        response.setCurrentPage(userPage.getNumber());

        return response;
    }
}
