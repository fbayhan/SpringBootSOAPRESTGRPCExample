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
public class BookDTO {

    private Long id;
    private String title;
    private String isbn;

    private String publisher;

    private String language;
    private int pages;

    private Long writerId;
    private List<Long> categoryIds;

    private List<CategoryDTO> categoryDTOS;
    private WriterDTO writerDTO;
}
