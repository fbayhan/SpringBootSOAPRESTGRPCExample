package com.library.grpc.service.impl;


import com.library.grpc.repository.BookRepository;
import com.library.grpc.repository.CategoryRepository;
import com.library.grpc.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl   {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private CategoryRepository categoryRepository;


}
