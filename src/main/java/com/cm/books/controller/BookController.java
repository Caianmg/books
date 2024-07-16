package com.cm.books.controller;

import com.cm.books.entity.*;
import com.cm.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/add")
    public String addBookForm(Model model) {
        return bookService.addBookForm(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return bookService.handleGetBookById(id);
    }

    @PostMapping("")
    public String createBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        return bookService.createBook(book, redirectAttributes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        return bookService.handleUpdateBook(id, updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        return bookService.handleDeleteBook(id);
    }

    @GetMapping("/search/title/{title}")
    public List<Book> findByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }

    @GetMapping("/search/author/{firstName}/{lastName}")
    public List<Book> findByAuthor(@PathVariable String firstName, @PathVariable String lastName) {
        return bookService.getBooksByAuthorName(firstName, lastName);
    }

    @GetMapping("/search/publisher/{name}")
    public List<Book> findByPublisher(@PathVariable String name) {
        return bookService.getBooksByPublisherName(name);
    }

    @GetMapping("/search/my-books/{myBooksId}")
    public List<Book> findByMyBooksId(@PathVariable int myBooksId) {
        return bookService.getBooksByMyBooksId(myBooksId);
    }

    @GetMapping("/book-list")
    public String findAllBooks(Model model) {
        return bookService.handleFindAllBooks(model);
    }

    @GetMapping("/my-books/{myBooksId}")
    public String showMyBooks(Model model, @PathVariable("myBooksId") int myBooksId) {
        return bookService.showMyBooks(model, myBooksId);
    }

    @PostMapping("/my-books/add")
    public String addToMyBooks(@RequestParam("bookId") int bookId, RedirectAttributes redirectAttributes) {
        return bookService.handleAddToMyBooks(bookId, redirectAttributes);
    }

}
