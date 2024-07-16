package com.cm.books.repository;

import com.cm.books.entity.MyBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyBooksRepository extends JpaRepository<MyBooks, Integer> {



}
