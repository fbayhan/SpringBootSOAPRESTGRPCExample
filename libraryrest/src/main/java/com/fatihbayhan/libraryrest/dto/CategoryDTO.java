package com.fatihbayhan.libraryrest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Long categoryId;

    @NotBlank(message = "Kategori adı boş olamaz")
    @Size(min = 2, max = 50, message = "Kategori adı 2 ile 50 karakter arasında olmalıdır")
    private String categoryName;
}
