package com.fatihbayhan.LibraryManagementSystem.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_writer")
@Getter
@Setter
public class Writer extends BaseEntity{
    private String fullName;
    private Date birthDate;
    private Date deathDate;
    private String nationality;
    private String biography;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY) //sunumda bahset
    private Set<Book> books;
}
