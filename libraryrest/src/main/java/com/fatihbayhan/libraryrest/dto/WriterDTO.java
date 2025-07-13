package com.fatihbayhan.libraryrest.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WriterDTO {
    private Long id;

    @NotBlank(message = "Yazar adı boş olamaz")
    @Size(min = 2, max = 100, message = "Yazar adı 2 ile 100 karakter arasında olmalıdır")
    private String fullName;

    @NotNull(message = "Doğum tarihi boş olamaz")
    @Past(message = "Doğum tarihi geçmiş bir tarih olmalıdır")
    private Date birthDate;

    @Past(message = "Ölüm tarihi geçmiş bir tarih olmalıdır")
    private Date deathDate;

    @NotBlank(message = "Milliyet boş olamaz")
    @Size(min = 2, max = 50, message = "Milliyet 2 ile 50 karakter arasında olmalıdır")
    private String nationality;

    @Size(max = 1000, message = "Biyografi en fazla 1000 karakter olabilir")
    private String biography;
}
