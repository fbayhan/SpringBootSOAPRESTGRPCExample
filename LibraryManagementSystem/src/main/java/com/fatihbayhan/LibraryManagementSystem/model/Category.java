package com.fatihbayhan.LibraryManagementSystem.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_category")
@Getter
@Setter
public class Category extends BaseEntity {
    private String categoryName;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY) //sunumda bahset
    private Set<Book> books;

 


}
