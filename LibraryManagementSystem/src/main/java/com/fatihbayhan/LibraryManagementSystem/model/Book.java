package com.fatihbayhan.LibraryManagementSystem.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_book")
@Getter
@Setter
public class Book extends BaseEntity{

    private String title;

    private String isbn;
    private String publisher;
    private String language;
    private int pages;


    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Writer writer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "book_categories",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    

}
