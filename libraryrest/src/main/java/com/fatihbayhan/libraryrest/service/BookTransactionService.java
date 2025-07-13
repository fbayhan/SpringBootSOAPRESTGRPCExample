package com.fatihbayhan.libraryrest.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.fatihbayhan.libraryrest.client.gen.BookBorrowingsResponse;
import com.fatihbayhan.libraryrest.client.gen.BookResponse;
import com.fatihbayhan.libraryrest.client.gen.BorrowBookRequest;
import com.fatihbayhan.libraryrest.client.gen.CategoryResponse;
import com.fatihbayhan.libraryrest.client.gen.GetAllBookBorrowingsRequest;
import com.fatihbayhan.libraryrest.client.gen.GetAllBookBorrowingsResponse;
import com.fatihbayhan.libraryrest.client.gen.ReturnBookRequest;
import com.fatihbayhan.libraryrest.client.gen.ReturnBookResponse;
import com.fatihbayhan.libraryrest.client.gen.UserResponse;
import com.fatihbayhan.libraryrest.client.gen.WriterResponse;
import com.fatihbayhan.libraryrest.dto.BookDTO;
import com.fatihbayhan.libraryrest.dto.BookTransactionDTO;
import com.fatihbayhan.libraryrest.dto.CategoryDTO;
import com.fatihbayhan.libraryrest.dto.UserDTO;
import com.fatihbayhan.libraryrest.dto.WriterDTO;

import jakarta.xml.bind.JAXBElement;

@Service
public class BookTransactionService {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    public List<BookTransactionDTO> getAllBookBorrowings() {
        GetAllBookBorrowingsRequest request = new GetAllBookBorrowingsRequest();
        Object response = webServiceTemplate.marshalSendAndReceive(request);

        if (response == null) {
            // Handle null response, maybe log an error or return empty list
            return Collections.emptyList();
        }

        // Ensure the response object is of the expected type before casting
        if (!(response instanceof JAXBElement)) {
             // Handle unexpected response type
             System.err.println("Unexpected response type: " + response.getClass().getName());
             return Collections.emptyList();
        }

        JAXBElement<?> jaxbElement = (JAXBElement<?>) response;
        if (!(jaxbElement.getValue() instanceof GetAllBookBorrowingsResponse)) {
            // Handle unexpected value type
            System.err.println("Unexpected JAXB value type: " + jaxbElement.getValue().getClass().getName());
            return Collections.emptyList();
        }


        GetAllBookBorrowingsResponse borrowingsResponse = ((JAXBElement<GetAllBookBorrowingsResponse>) response).getValue();

        if (borrowingsResponse == null || borrowingsResponse.getBookBorrowingResponse() == null) {
            return Collections.emptyList(); // Veya uygun bir hata işleme mekanizması
        }

        return borrowingsResponse.getBookBorrowingResponse().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    

    private BookTransactionDTO convertToDto(BookBorrowingsResponse borrowingResponse) {
        if (borrowingResponse == null) {
            return null;
        }

        BookTransactionDTO dto = new BookTransactionDTO();
        dto.setTransactionId(Long.parseLong(borrowingResponse.getBookBorrowingId()));
        dto.setIsReturned(borrowingResponse.isIsReturned());

        // Null kontrolleri ile tarih dönüşümleri
        if (borrowingResponse.getBorrowDate() != null) {
            dto.setBorrowDate(borrowingResponse.getBorrowDate().toGregorianCalendar().getTime());
        }
        if (borrowingResponse.getMustReturnDate() != null) {
            dto.setMustReturnDate(borrowingResponse.getMustReturnDate().toGregorianCalendar().getTime());
        }

        // Nested DTO dönüşümleri (Null kontrolleri ile)
        if (borrowingResponse.getBook() != null) {
            dto.setBook(convertBookResponseToDto(borrowingResponse.getBook()));
            dto.setBookId(dto.getBook().getId()); // Book ID'yi de ayarlayalım
        }
        if (borrowingResponse.getUser() != null) {
            dto.setUser(convertUserResponseToDto(borrowingResponse.getUser()));
            dto.setUserId(dto.getUser().getId()); // User ID'yi de ayarlayalım
        }


        return dto;
    }

    // BookResponse'u BookDTO'ya çeviren yardımcı metot
    private BookDTO convertBookResponseToDto(BookResponse bookResponse) {
        if (bookResponse == null) {
            return null;
        }
        BookDTO dto = new BookDTO();
        dto.setId(Long.parseLong(bookResponse.getBookId()));
        dto.setTitle(bookResponse.getTitle());
        dto.setIsbn(bookResponse.getIsbn());
        dto.setPublisher(bookResponse.getPublisher());
        dto.setLanguage(bookResponse.getLanguage());
        dto.setPages(bookResponse.getPages());

        // WriterDTO dönüşümü (Null kontrolü ile)
        if (bookResponse.getWriter() != null) {
            dto.setWriterDTO(convertWriterResponseToDto(bookResponse.getWriter()));
             dto.setWriterId(dto.getWriterDTO().getId()); // Writer ID'yi de ayarlayalım
        }


        // CategoryDTO listesi dönüşümü (Null kontrolü ile)
        if (bookResponse.getCategories() != null) {
             dto.setCategoryDTOS(bookResponse.getCategories().stream()
                .map(this::convertCategoryResponseToDto)
                .collect(Collectors.toList()));
             // CategoryIds listesini de ayarlayalım
             dto.setCategoryIds(dto.getCategoryDTOS().stream()
                                   .map(CategoryDTO::getCategoryId)
                                   .collect(Collectors.toList()));
        } else {
            dto.setCategoryDTOS(Collections.emptyList());
            dto.setCategoryIds(Collections.emptyList());
        }



        return dto;
    }

    // WriterResponse'u WriterDTO'ya çeviren yardımcı metot
    private WriterDTO convertWriterResponseToDto(WriterResponse writerResponse) {
         if (writerResponse == null) {
            return null;
        }
        WriterDTO dto = new WriterDTO();
        dto.setId(Long.parseLong(writerResponse.getWriterId()));
        dto.setFullName(writerResponse.getFullName());
        dto.setNationality(writerResponse.getNationality());
        dto.setBiography(writerResponse.getBiography());

        // Tarih dönüşümleri (Null kontrolü ile)
        if (writerResponse.getBirthDate() != null) {
            dto.setBirthDate(writerResponse.getBirthDate().toGregorianCalendar().getTime());
        }
        if (writerResponse.getDeathDate() != null) {
            dto.setDeathDate(writerResponse.getDeathDate().toGregorianCalendar().getTime());
        }


        return dto;
    }

     // CategoryResponse'u CategoryDTO'ya çeviren yardımcı metot
    private CategoryDTO convertCategoryResponseToDto(CategoryResponse categoryResponse) {
        if (categoryResponse == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(Long.parseLong(categoryResponse.getCategoryId()));
        dto.setCategoryName(categoryResponse.getCategoryName());
        return dto;
    }


    // UserResponse'u UserDTO'ya çeviren yardımcı metot
    private UserDTO convertUserResponseToDto(UserResponse userResponse) {
         if (userResponse == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(Long.parseLong(userResponse.getUserId()));
        dto.setIdentityNumber(userResponse.getIdentityNumber());
        dto.setFullName(userResponse.getFullName());
        dto.setEmail(userResponse.getEmail());
        return dto;
    }

    // Kitap iade etme metodu
    public boolean returnBook(Long bookBorrowingId) {
        ReturnBookRequest request = new ReturnBookRequest();
        request.setBookBorrowingId(bookBorrowingId.toString());

        try {
            Object response = webServiceTemplate.marshalSendAndReceive(request);

            if (response instanceof JAXBElement) {
                JAXBElement<?> jaxbElement = (JAXBElement<?>) response;
                if (jaxbElement.getValue() instanceof ReturnBookResponse) {
                    ReturnBookResponse returnResponse = (ReturnBookResponse) jaxbElement.getValue();
                    // WSDL'ye göre response'da success alanı yok, 
                    // ancak isReturned alanı var. Bu alanı kontrol edebiliriz.
                    // Veya basitçe isteğin başarılı olduğunu varsayabiliriz.
                    // Şimdilik, hata almazsak başarılı kabul edelim.
                     // İsteğe bağlı: returnResponse.isIsReturned() gibi bir kontrol eklenebilir
                    return true; // Başarılı
                }
            }
             // Beklenmeyen yanıt tipi veya hata durumu
            System.err.println("Unexpected response type or error during book return for ID: " + bookBorrowingId);
            return false;
        } catch (Exception e) {
            // SOAP isteği sırasında bir hata oluştu
            System.err.println("Error calling SOAP service for returnBook with ID: " + bookBorrowingId + "; Error: " + e.getMessage());
            // Hata loglanabilir
            // Logger.error("SOAP call failed for returnBook", e);
            return false;
        }
    }

    // Yeni eklenecek metot: Kitap Ödünç Alma
    public void borrowBook(Long bookId, Long userId) {
        BorrowBookRequest request = new BorrowBookRequest();
        request.setBookId(bookId.toString());
        request.setUserId(userId.toString());

        try {
            // WSDL'ye göre bu operasyonun belirli bir response mesajı yok gibi görünüyor.
            // marshalSendAndReceive bir yanıt bekleyecektir, null veya basit bir onay dönebilir.
            // Şimdilik dönen yanıtı işlemiyoruz, sadece isteği gönderiyoruz.
            webServiceTemplate.marshalSendAndReceive(request);
            // Başarılı olursa loglama yapılabilir.
             System.out.println("Book borrowing request sent successfully for Book ID: " + bookId + ", User ID: " + userId);

        } catch (Exception e) {
            // SOAP isteği sırasında bir hata oluştu
            System.err.println("Error calling SOAP service for borrowBook. Book ID: " + bookId + ", User ID: " + userId + "; Error: " + e.getMessage());
            // Hata loglanabilir ve isteğe bağlı olarak özel bir exception fırlatılabilir.
            // throw new RuntimeException("Failed to borrow book due to SOAP service error.", e);
        }
    }
}
