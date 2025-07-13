package com.fatihbayhan.libraryrest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTransactionDTO {
    private Long bookId;
    private Long transactionId;
    private Long userId;
    private BookDTO book;
    private UserDTO user;
    private Date borrowDate;
    private Date mustReturnDate;
    private Boolean isReturned;


}
