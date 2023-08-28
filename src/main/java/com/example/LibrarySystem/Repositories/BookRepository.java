package com.example.LibrarySystem.Repositories;

import com.example.LibrarySystem.Enums.Genre;
import com.example.LibrarySystem.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findBooksByGenre(Genre genre);

    List<Book> findBooksByIsAvailableFalse();
}
