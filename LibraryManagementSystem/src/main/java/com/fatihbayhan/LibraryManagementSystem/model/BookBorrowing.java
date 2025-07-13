package com.fatihbayhan.LibraryManagementSystem.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="tbl_book_borrowing")
public class BookBorrowing extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private Date borrowDate;

    private Date returnDate;

    private Date mustReturnDate;

    private Boolean isReturned;

    

}
