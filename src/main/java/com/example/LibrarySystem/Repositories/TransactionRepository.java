package com.example.LibrarySystem.Repositories;

import com.example.LibrarySystem.Enums.TransactionStatus;
import com.example.LibrarySystem.Enums.TransactionType;
import com.example.LibrarySystem.Models.Book;
import com.example.LibrarySystem.Models.LibraryCard;
import com.example.LibrarySystem.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


}