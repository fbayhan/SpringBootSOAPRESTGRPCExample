package com.fatihbayhan.LibraryManagementSystem.service;

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

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
    GetUserResponse getUser(GetUserRequest request);
    GetAllUsersResponse getAllUsers(GetAllUsersRequest request);
    UpdateUserResponse updateUser(UpdateUserRequest request);
    DeleteUserResponse deleteUser(DeleteUserRequest request);
    SearchUsersResponse searchUsers(SearchUsersRequest request);
}
