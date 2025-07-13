package com.fatihbayhan.libraryrest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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