package com.fatihbayhan.libraryrest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.fatihbayhan.libraryrest.client.gen.CreateUserRequest;
import com.fatihbayhan.libraryrest.client.gen.CreateUserResponse;
import com.fatihbayhan.libraryrest.client.gen.DeleteUserRequest;
import com.fatihbayhan.libraryrest.client.gen.DeleteUserResponse;
import com.fatihbayhan.libraryrest.client.gen.GetAllUsersRequest;
import com.fatihbayhan.libraryrest.client.gen.GetAllUsersResponse;
import com.fatihbayhan.libraryrest.client.gen.GetUserRequest;
import com.fatihbayhan.libraryrest.client.gen.SearchUsersRequest;
import com.fatihbayhan.libraryrest.client.gen.SearchUsersResponse;
import com.fatihbayhan.libraryrest.client.gen.UpdateUserRequest;
import com.fatihbayhan.libraryrest.client.gen.UpdateUserResponse;
import com.fatihbayhan.libraryrest.dto.UserDTO;
import com.fatihbayhan.libraryrest.dto.UserSearchResponseDTO;

import jakarta.xml.bind.JAXBElement;

@Service
public class UserService {
    
    @Autowired
    private WebServiceTemplate webServiceTemplate;
    
    public UserDTO createUser(UserDTO userDTO) {
        CreateUserRequest request = new CreateUserRequest();
        request.setIdentityNumber(userDTO.getIdentityNumber());
        request.setFullName(userDTO.getFullName());
        request.setEmail(userDTO.getEmail());
        request.setPassword(userDTO.getPassword());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        CreateUserResponse userResponse = ((CreateUserResponse) response);
        
        UserDTO responseDTO = new UserDTO();
        responseDTO.setId(Long.parseLong(userResponse.getUserId()));
        responseDTO.setIdentityNumber(userResponse.getIdentityNumber());
        responseDTO.setFullName(userResponse.getFullName());
        responseDTO.setEmail(userResponse.getEmail());
        return responseDTO;
    }

    public UserDTO getUser(Long userId) {
        GetUserRequest request = new GetUserRequest();
        request.setUserId(userId.toString());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        CreateUserResponse userResponse = ((JAXBElement<CreateUserResponse>) response).getValue();
        
        UserDTO responseDTO = new UserDTO();
        responseDTO.setId(Long.parseLong(userResponse.getUserId()));
        responseDTO.setIdentityNumber(userResponse.getIdentityNumber());
        responseDTO.setFullName(userResponse.getFullName());
        responseDTO.setEmail(userResponse.getEmail());
        return responseDTO;
    }

    public UserDTO updateUser(UserDTO userDTO) {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setUserId(userDTO.getId().toString());
        request.setIdentityNumber(userDTO.getIdentityNumber());
        request.setFullName(userDTO.getFullName());
        request.setEmail(userDTO.getEmail());
        //request.setPassword(userDTO.getPassword());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        UpdateUserResponse userResponse = ((UpdateUserResponse) response);
        
        UserDTO responseDTO = new UserDTO();
        responseDTO.setId(Long.parseLong(userResponse.getUserId()));
        responseDTO.setIdentityNumber(userResponse.getIdentityNumber());
        responseDTO.setFullName(userResponse.getFullName());
        responseDTO.setEmail(userResponse.getEmail());
        return responseDTO;
    }

    public boolean deleteUser(Long userId) {
        DeleteUserRequest request = new DeleteUserRequest();
        request.setUserId(userId.toString());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        DeleteUserResponse deleteResponse = ((JAXBElement<DeleteUserResponse>) response).getValue();
        
        return deleteResponse.isSuccess();
    }

    public List<UserDTO> getAllUsers() {
        GetAllUsersRequest request = new GetAllUsersRequest();
        Object response = webServiceTemplate.marshalSendAndReceive(request);
        GetAllUsersResponse usersResponse = ((JAXBElement<GetAllUsersResponse>) response).getValue();
        
        return usersResponse.getUsers().stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(Long.parseLong(user.getUserId()));
                    dto.setIdentityNumber(user.getIdentityNumber());
                    dto.setFullName(user.getFullName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public UserSearchResponseDTO searchUsers(String searchTerm, int page, int size) {
        SearchUsersRequest request = new SearchUsersRequest();
        request.setSearchTerm(searchTerm);
        request.setPage(0);
        request.setSize(10);

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        SearchUsersResponse searchResponse = ((SearchUsersResponse) response);

        List<UserDTO> userDTOs = searchResponse.getUsers().stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(Long.parseLong(user.getUserId()));
                    dto.setIdentityNumber(user.getIdentityNumber());
                    dto.setFullName(user.getFullName());
                    dto.setEmail(user.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());

        UserSearchResponseDTO responseDTO = new UserSearchResponseDTO();
        responseDTO.setUsers(userDTOs);
        responseDTO.setTotalElements(searchResponse.getTotalElements());
        responseDTO.setTotalPages(searchResponse.getTotalPages());
        responseDTO.setCurrentPage(searchResponse.getCurrentPage());

        return responseDTO;
    }

}
