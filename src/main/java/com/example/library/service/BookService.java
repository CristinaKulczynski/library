package com.example.library.service;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.library.model.Book;
import com.example.library.repository.ReservationRepository;

@Service
public class BookService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public BookService(
            RestTemplate restTemplate,
            ReservationRepository reservationRepository,
            @Value("${external.api.books:http://localhost:8081/books}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

  
    public List<Book> findAvailableBooks() {
        String url = apiUrl + "?booked=0";
        Book[] booksArray = restTemplate.getForObject(url, Book[].class);
        return booksArray != null ? Arrays.asList(booksArray) : List.of();
    }

    public List<Book> findAllReservedBooks() {
        String url = apiUrl + "?booked=1";
        Book[] booksArray = restTemplate.getForObject(url, Book[].class);
        return booksArray != null ? Arrays.asList(booksArray) : List.of();
    }

    public void reserveBook(String code) {
        
        String url = apiUrl + "/" + code + "/1";
        restTemplate.put(url, null);
    }

   public void returnBook(String code) {
        String url = apiUrl + "/" + code + "/0";
        restTemplate.put(url, null);
    }

    
}

