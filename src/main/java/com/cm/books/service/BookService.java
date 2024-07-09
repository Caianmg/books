package com.cm.books.service;

import com.cm.books.entity.Book;
import com.cm.books.entity.MyBooks;
import com.cm.books.repository.BookRepository;
import com.cm.books.repository.MyBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MyBooksRepository myBooksRepository;

    @Autowired
    public BookService(BookRepository bookRepository, MyBooksRepository myBooksRepository) {
        this.bookRepository = bookRepository;
        this.myBooksRepository = myBooksRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> getBooksByAuthorName(String firstName, String lastName) {
        return bookRepository.findByAuthorFirstNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCase(firstName, lastName);
    }

    public List<Book> getBooksByPublisherName(String name) {
        return bookRepository.findByPublisherNameContainingIgnoreCase(name);
    }

    public List<Book> getBooksByMyBooksId(int myBooksId) {
        return bookRepository.findByMyBooksId(myBooksId);
    }

    public void saveBook(Book book) {
        Optional<Book> existingBook = bookRepository.findByTitleAndAuthorFirstNameAndAuthorLastNameAndPublisherName(
                book.getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName(), book.getPublisher().getName());

        if (existingBook.isPresent()) {
            throw new IllegalArgumentException("Livro já existe com o mesmo título, autor e editora.");
        }
        bookRepository.save(book);
    }

    public Book updateBook(int id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            updatedBook.setId(id);
            return bookRepository.save(updatedBook);
        } else {
            return null;
        }
    }

    public boolean deleteBook(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean addToMyBooks(int bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        MyBooks myBooks = createMyBooks();
        if (optionalBook.isPresent() && myBooks != null) {
            Book book = optionalBook.get();
            book.setMyBooks(myBooks);
            bookRepository.save(book);
            return true;
        } else {
            return false;
        }
    }

    public MyBooks createMyBooks() {
        Optional<MyBooks> existingMyBooks = myBooksRepository.findById(1);
        if (existingMyBooks.isPresent()) {
            return existingMyBooks.get();
        } else {
            MyBooks myBooks = new MyBooks();
            return myBooksRepository.save(myBooks);
        }
    }

}
