package com.fatihbayhan.libraryrest.service;

import java.util.List;
import java.util.stream.Collectors;

import com.fatihbayhan.libraryrest.client.gen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.fatihbayhan.libraryrest.dto.BookDTO;
import com.fatihbayhan.libraryrest.dto.CategoryDTO;
import com.fatihbayhan.libraryrest.dto.WriterDTO;

import jakarta.xml.bind.JAXBElement;

@Service
public class BookService {
    
    @Autowired
    private WebServiceTemplate webServiceTemplate;
    
    public BookDTO createBook(BookDTO bookDTO) {
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle(bookDTO.getTitle());
        request.setIsbn(bookDTO.getIsbn());
        request.setPublisher(bookDTO.getPublisher());
        request.setLanguage(bookDTO.getLanguage());
        request.setPages(bookDTO.getPages());
        request.setWriterId(bookDTO.getWriterId().toString());
        request.getCategoryIds().addAll(bookDTO.getCategoryIds().stream()
                .map(String::valueOf)
                .collect(Collectors.toList()));

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        BookResponse bookResponse = ((JAXBElement<BookResponse>) response).getValue();
        
        return convertToDTO(bookResponse);
    }

    public BookDTO getBook(Long bookId) {
        GetBookRequest request = new GetBookRequest();
        request.setBookId(bookId.toString());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        BookResponse bookResponse = ((JAXBElement<BookResponse>) response).getValue();
        
        return convertToDTO(bookResponse);
    }

    public BookDTO updateBook(BookDTO bookDTO) {
        UpdateBookRequest request = new UpdateBookRequest();
        request.setBookId(bookDTO.getId().toString());
        request.setTitle(bookDTO.getTitle());
        request.setIsbn(bookDTO.getIsbn());
        request.setPublisher(bookDTO.getPublisher());
        request.setLanguage(bookDTO.getLanguage());
        request.setPages(bookDTO.getPages());
        request.setWriterId(bookDTO.getWriterId().toString());
        request.getCategoryIds().addAll(bookDTO.getCategoryIds().stream()
                .map(String::valueOf)
                .collect(Collectors.toList()));

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        BookResponse bookResponse = ((JAXBElement<BookResponse>) response).getValue();
        
        return convertToDTO(bookResponse);
    }

    public boolean deleteBook(Long bookId) {
        DeleteBookRequest request = new DeleteBookRequest();
        request.setBookId(bookId.toString());

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        DeleteBookResponse deleteResponse = ((JAXBElement<DeleteBookResponse>) response).getValue();
        
        return deleteResponse.isSuccess();
    }

    public List<BookDTO> getAllBooks() {
        GetAllBooksRequest request = new GetAllBooksRequest();
        Object response = webServiceTemplate.marshalSendAndReceive(request);
        GetAllBooksResponse booksResponse = ((JAXBElement<GetAllBooksResponse>) response).getValue();
        
        return booksResponse.getBooks().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BookDTO convertToDTO(BookResponse bookResponse) {
        BookDTO dto = new BookDTO();
        dto.setId(Long.parseLong(bookResponse.getBookId()));
        dto.setTitle(bookResponse.getTitle());
        dto.setIsbn(bookResponse.getIsbn());
        dto.setPublisher(bookResponse.getPublisher());
        dto.setLanguage(bookResponse.getLanguage());
        dto.setPages(bookResponse.getPages());
        
        // WriterDTO'yu ayarla
        WriterDTO writerDTO = new WriterDTO();
        writerDTO.setId(Long.parseLong(bookResponse.getWriter().getWriterId()));
        writerDTO.setFullName(bookResponse.getWriter().getFullName());
        if (bookResponse.getWriter().getBirthDate() != null) {
            writerDTO.setBirthDate(bookResponse.getWriter().getBirthDate().toGregorianCalendar().getTime());
        }
        if (bookResponse.getWriter().getDeathDate() != null) {
            writerDTO.setDeathDate(bookResponse.getWriter().getDeathDate().toGregorianCalendar().getTime());
        }
        writerDTO.setNationality(bookResponse.getWriter().getNationality());
        writerDTO.setBiography(bookResponse.getWriter().getBiography());
        dto.setWriterDTO(writerDTO);
        
        // CategoryDTO'ları ayarla
        List<CategoryDTO> categoryDTOs = bookResponse.getCategories().stream()
                .map(category -> {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setCategoryId(Long.parseLong(category.getCategoryId()));
                    categoryDTO.setCategoryName(category.getCategoryName());
                    return categoryDTO;
                })
                .collect(Collectors.toList());
        dto.setCategoryDTOS(categoryDTOs);
        
        // CategoryIds'i ayarla
        dto.setCategoryIds(bookResponse.getCategories().stream()
                .map(category -> Long.parseLong(category.getCategoryId()))
                .collect(Collectors.toList()));
                
        return dto;
    }

    public List<BookDTO> searchBooks(String searchTerm) {
        SearchBooksRequest request = new SearchBooksRequest();
        request.setSearchTerm(searchTerm);
        request.setPage(0);
        request.setSize(10);

        Object response = webServiceTemplate.marshalSendAndReceive(request);
        SearchBooksResponse booksResponse = ((JAXBElement<SearchBooksResponse>) response).getValue();

        return booksResponse.getBooks().stream()
                .map(book -> {
                    BookDTO dto = new BookDTO();
                    dto.setId(Long.parseLong(book.getBookId()));
                    dto.setTitle(book.getTitle());
                    dto.setIsbn(book.getIsbn());
                    dto.setPublisher(book.getPublisher());
                    dto.setLanguage(book.getLanguage());
                    dto.setPages(book.getPages());

//                    WriterDTO writerDTO = new WriterDTO();
//                    writerDTO.setId(Long.parseLong(book.getWriter().getWriterId()));
//                    writerDTO.setFullName(book.getWriter().getFullName());
//                    writerDTO.setBirthDate(book.getWriter().getBirthDate());
//                    writerDTO.setDeathDate(book.getWriter().getDeathDate());
//                    writerDTO.setNationality(book.getWriter().getNationality());
//                    writerDTO.setBiography(book.getWriter().getBiography());
//                    dto.setWriter(writerDTO);
//
//                    List<CategoryDTO> categories = book.getCategories().stream()
//                            .map(category -> {
//                                CategoryDTO categoryDTO = new CategoryDTO();
//                                categoryDTO.setId(Long.parseLong(category.getCategoryId()));
//                                categoryDTO.setName(category.getCategoryName());
//                                return categoryDTO;
//                            })
//                            .collect(Collectors.toList());
//                    dto.setCategories(categories);

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
