package com.fatihbayhan.LibraryManagementSystem.service.impl;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fatihbayhan.LibraryManagementSystem.model.Book;
import com.fatihbayhan.LibraryManagementSystem.model.Category;
import com.fatihbayhan.LibraryManagementSystem.model.Writer;
import com.fatihbayhan.LibraryManagementSystem.repository.BookRepository;
import com.fatihbayhan.LibraryManagementSystem.repository.CategoryRepository;
import com.fatihbayhan.LibraryManagementSystem.repository.WriterRepository;
import com.fatihbayhan.LibraryManagementSystem.service.BookService;
import com.fatihbayhan.librarymanagement.BookResponse;
import com.fatihbayhan.librarymanagement.CreateBookRequest;
import com.fatihbayhan.librarymanagement.DeleteBookRequest;
import com.fatihbayhan.librarymanagement.DeleteBookResponse;
import com.fatihbayhan.librarymanagement.GetAllBooksRequest;
import com.fatihbayhan.librarymanagement.GetAllBooksResponse;
import com.fatihbayhan.librarymanagement.GetBookRequest;
import com.fatihbayhan.librarymanagement.SearchBooksRequest;
import com.fatihbayhan.librarymanagement.SearchBooksResponse;
import com.fatihbayhan.librarymanagement.UpdateBookRequest;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public BookResponse createBook(CreateBookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublisher(request.getPublisher());
        book.setLanguage(request.getLanguage());
        book.setPages(request.getPages());

        Writer writer = writerRepository.findById(Long.parseLong(request.getWriterId()))
                .orElseThrow(() -> new RuntimeException("Writer not found"));
        book.setWriter(writer);

        if (request.getCategoryIds() != null) {
            List<Category> categories = request.getCategoryIds().stream()
                    .map(id -> categoryRepository.findById(Long.parseLong(id))
                            .orElseThrow(() -> new RuntimeException("Category not found")))
                    .collect(Collectors.toList());
            book.setCategories(new HashSet<>(categories));
        }

        Book savedBook = bookRepository.save(book);
        return convertToBookResponse(savedBook);
    }

    @Override
    public BookResponse getBook(GetBookRequest request) {
        Book book = bookRepository.findById(Long.parseLong(request.getBookId()))
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return convertToBookResponse(book);
    }

    @Override
    public GetAllBooksResponse getAllBooks(GetAllBooksRequest request) {
        List<Book> books = bookRepository.findAll();
        GetAllBooksResponse response = new GetAllBooksResponse();
        response.getBooks().addAll(books.stream()
                .map(this::convertToBookResponse)
                .collect(Collectors.toList()));
        return response;
    }

    @Override
    public BookResponse updateBook(UpdateBookRequest request) {
        Book book = bookRepository.findById(Long.parseLong(request.getBookId()))
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublisher(request.getPublisher());
        book.setLanguage(request.getLanguage());
        book.setPages(request.getPages());

        Writer writer = writerRepository.findById(Long.parseLong(request.getWriterId()))
                .orElseThrow(() -> new RuntimeException("Writer not found"));
        book.setWriter(writer);

        if (request.getCategoryIds() != null) {
            List<Category> categories = request.getCategoryIds().stream()
                    .map(id -> categoryRepository.findById(Long.parseLong(id))
                            .orElseThrow(() -> new RuntimeException("Category not found")))
                    .collect(Collectors.toList());
            book.setCategories(new HashSet<>(categories));
        }

        Book updatedBook = bookRepository.save(book);
        return convertToBookResponse(updatedBook);
    }

    @Override
    public DeleteBookResponse deleteBook(DeleteBookRequest request) {
        bookRepository.deleteById(Long.parseLong(request.getBookId()));
        DeleteBookResponse response = new DeleteBookResponse();
        response.setSuccess(true);
        return response;
    }

    @Override
    public SearchBooksResponse searchBooks(SearchBooksRequest request) {
        Page<Book> bookPage = bookRepository.findByTitleContainingIgnoreCase(
                request.getSearchTerm(),
                PageRequest.of(request.getPage(), request.getSize()));

        SearchBooksResponse response = new SearchBooksResponse();
        response.getBooks().addAll(bookPage.getContent().stream()
                .map(this::convertToBookResponse)
                .collect(Collectors.toList()));
        response.setTotalElements(bookPage.getTotalElements());
        response.setTotalPages(bookPage.getTotalPages());
        response.setCurrentPage(bookPage.getNumber());
        return response;
    }

    private XMLGregorianCalendar convertToXMLGregorianCalendar(java.util.Date date) {
        if (date == null) return null;
        try {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            throw new RuntimeException("Error converting date to XMLGregorianCalendar", e);
        }
    }

    private BookResponse convertToBookResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setBookId(String.valueOf(book.getId()));
        response.setTitle(book.getTitle());
        response.setIsbn(book.getIsbn());
        response.setPublisher(book.getPublisher());
        response.setLanguage(book.getLanguage());
        response.setPages(book.getPages());

        Writer writer = book.getWriter();
        if (writer != null) {
            com.fatihbayhan.librarymanagement.WriterResponse writerResponse = new com.fatihbayhan.librarymanagement.WriterResponse();
            writerResponse.setWriterId(String.valueOf(writer.getId()));
            writerResponse.setFullName(writer.getFullName());
            writerResponse.setBirthDate(convertToXMLGregorianCalendar(writer.getBirthDate()));
            writerResponse.setDeathDate(convertToXMLGregorianCalendar(writer.getDeathDate()));
            writerResponse.setNationality(writer.getNationality());
            writerResponse.setBiography(writer.getBiography());
            response.setWriter(writerResponse);
        }

        if (book.getCategories() != null) {
            book.getCategories().forEach(category -> {
                com.fatihbayhan.librarymanagement.CategoryResponse categoryResponse = new com.fatihbayhan.librarymanagement.CategoryResponse();
                categoryResponse.setCategoryId(String.valueOf(category.getId()));
                categoryResponse.setCategoryName(category.getCategoryName());
                response.getCategories().add(categoryResponse);
            });
        }

        return response;
    }
}
