package com.fatihbayhan.libraryrest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String identityNumber;
    private String fullName;
    private String email;

    private String password;
    private int borrowCount;
    private int  readCount;

} 