package com.example.library.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.library.model.Book;
import com.example.library.service.BookService;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
   
    @GetMapping("/books")
    public String listBooks(Model model) {
        List<Book> allBooks = bookService.findAvailableBooks();
        model.addAttribute("books", allBooks);
        return "books";
    }

    @GetMapping("/reservar")
    public String showReservePage(Model model) {
        List<Book> availableBooks = bookService.findAvailableBooks();
        model.addAttribute("books", availableBooks);
        return "reservar";
    }

    @PostMapping("/reservar/{code}")
    public String reserveBook(@PathVariable("code") String code, Principal principal, Model model) {
     
        bookService.reserveBook(code);
        return "redirect:/reservar";
    }
  
    @GetMapping("/return_book")
    public String showReturnPage(Model model, Principal principal) {
        List<Book> reservedBooks = bookService.findAllReservedBooks();
        model.addAttribute("books", reservedBooks);
        return "return_book"; 
    }

    @PostMapping("/return_book/{code}")
    public String returnBook(@PathVariable("code") String code, Principal principal, Model model) {
      
        bookService.returnBook(code);
        return "redirect:/return_book";
    }
}
