package com.example.Repositories;

import com.example.Suppliers.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByNameAndAuthor(String name, String author);
    Optional<Book> findByISBN(String isbn);
}