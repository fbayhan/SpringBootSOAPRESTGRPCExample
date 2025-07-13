package com.library.grpc.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WriterDTO {
    private Long id;


    private String fullName;


    private Date birthDate;


    private Date deathDate;


    private String nationality;


    private String biography;
}
