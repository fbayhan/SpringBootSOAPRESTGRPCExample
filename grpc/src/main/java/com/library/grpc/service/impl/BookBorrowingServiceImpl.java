package com.library.grpc.service.impl;


import com.library.grpc.repository.BookBorrowingRepository;
import com.library.grpc.repository.BookRepository;
import com.library.grpc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class BookBorrowingServiceImpl   {

    @Autowired
    private BookBorrowingRepository bookBorrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;


}
