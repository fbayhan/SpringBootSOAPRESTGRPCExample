package com.library.grpc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tbl_category")
@Getter
@Setter
public class Category extends BaseEntity {
    private String categoryName;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY) //sunumda bahset
    private Set<Book> books;

 


}
