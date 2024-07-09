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
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public String createBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("message", "Livro adicionado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/books/add";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        Book book = bookService.updateBook(id, updatedBook);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book-list";
    }

    @GetMapping("/my-books/{myBooksId}")
    public String showMyBooks(Model model, @PathVariable("myBooksId") int myBooksId) {
        List<Book> myBooks = bookService.getBooksByMyBooksId(myBooksId);
        model.addAttribute("myBooks", myBooks);
        return "my-books";
    }

    @PostMapping("/my-books/add")
    public String addToMyBooks(@RequestParam("bookId") int bookId, RedirectAttributes redirectAttributes) {
        boolean added = bookService.addToMyBooks(bookId);
        if (added) {
            redirectAttributes.addFlashAttribute("message", "Livro adicionado aos Meus Livros com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Falha ao adicionar o livro aos Meus Livros. Por favor, tente novamente.");
        }
        return "redirect:/books/book-list";
    }

}
