package com.cm.books.repository;

import com.cm.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorFirstNameContainingIgnoreCaseOrAuthorLastNameContainingIgnoreCase(String firstName, String lastName);

    List<Book> findByPublisherNameContainingIgnoreCase(String name);

    List<Book> findByMyBooksId(int myBooksId);

    Optional<Book> findByTitleAndAuthorFirstNameAndAuthorLastNameAndPublisherName(String title, String authorFirstName, String authorLastName, String publisherName);

}
