package com.library.grpc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResponseDTO {
    private List<UserDTO> users;
    private long totalElements;
    private int totalPages;
    private int currentPage;
} 