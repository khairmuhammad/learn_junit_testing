package com.example.learn_junit_testing.controller;

import com.example.learn_junit_testing.entity.Book;
import com.example.learn_junit_testing.exception.NotFoundException;
import com.example.learn_junit_testing.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBookRecords() {
        return bookRepository.findAll();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    @PostMapping
    public Book createBookRecord(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) throws NotFoundException {
        if(book == null || book.getBookId() == null) {
            throw new NotFoundException("BookRecord or ID must not be null");
        }
        Optional<Book> optionalBook = bookRepository.findById(book.getBookId());
        if (!optionalBook.isPresent()) throw new NotFoundException("Book with ID: " + book.getBookId() + " does not exists.");

        Book existingBook = optionalBook.get();
        BeanUtils.copyProperties(book, existingBook);
        return bookRepository.save(existingBook);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBookById(@PathVariable Long bookId) throws NotFoundException {
        if(!bookRepository.findById(bookId).isPresent()) throw new NotFoundException("bookId: " + bookId + " not present.");
        bookRepository.deleteById(bookId);
    }
}
