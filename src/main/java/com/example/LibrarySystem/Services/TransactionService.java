package com.example.LibrarySystem.Services;


import com.example.LibrarySystem.CustomExceptions.BookNotAvailableException;
import com.example.LibrarySystem.CustomExceptions.BookNotFoundException;
import com.example.LibrarySystem.Enums.CardStatus;
import com.example.LibrarySystem.Enums.TransactionStatus;
import com.example.LibrarySystem.Enums.TransactionType;
import com.example.LibrarySystem.Models.Book;
import com.example.LibrarySystem.Models.LibraryCard;
import com.example.LibrarySystem.Models.Transaction;
import com.example.LibrarySystem.Repositories.BookRepository;
import com.example.LibrarySystem.Repositories.CardRepository;
import com.example.LibrarySystem.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Value("${book.maxLimit}")
    private Integer maxBookLimit;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CardRepository cardRepository;



    public static final Integer bookLimit = 6;




    public String issueBook(Integer bookId,Integer cardId)throws Exception{

        //Book Related Exception Handling

        Transaction transaction = new Transaction(TransactionStatus.PENDING, TransactionType.ISSUE,0);

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(!optionalBook.isPresent()){
            throw new BookNotFoundException("Book Id is incorrect");
        }
        Book book = optionalBook.get();
        if(book.getIsAvailable()==Boolean.FALSE){
            throw new BookNotAvailableException("Book is not Available");
        }


        //Card Related Exception Handling
        Optional<LibraryCard> optionalLibraryCard = cardRepository.findById(cardId);

        if(!optionalLibraryCard.isPresent()){
            throw new Exception("Card Id entered is Invalid");
        }

        LibraryCard card = optionalLibraryCard.get();
        if(!card.getCardStatus().equals(CardStatus.ACTIVE)){

            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction = transactionRepository.save(transaction);

            throw new Exception("Card is not in Right status");
        }
        if(card.getNoOfBooksIssued()>=bookLimit){

            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction = transactionRepository.save(transaction);
            throw new Exception("Already max Limit Books are issued");
        }

        /*  All the failed cases and invalid scenarios are over */

        //We have reached at a success point

        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        //update the card and book Entity
        book.setIsAvailable(Boolean.FALSE);
        card.setNoOfBooksIssued(card.getNoOfBooksIssued()+1);

        //We need to do unidirectional mappings :-->
        transaction.setBook(book);
        transaction.setLibraryCard(card);


        Transaction newTransactionWithId = transactionRepository.save(transaction);

        //We need to do in the parent classes
        book.getTransactionList().add(newTransactionWithId);

        card.getTransactionList().add(newTransactionWithId);


        bookRepository.save(book);
        cardRepository.save(card);

        //What all needs to saved
        return "Transaction has been saved successfully";


    }
    public String returnBook(Integer bookId,Integer cardId){

        Book book = bookRepository.findById(bookId).get();
        LibraryCard card = cardRepository.findById(cardId).get();


        List<Transaction> transactionList = transactionRepository.findTransactionsByBookAndLibraryCardAndTransactionStatusAndTransactionType(book,card,TransactionStatus.SUCCESS,TransactionType.ISSUE);

        //last transaction
        Transaction latestTransaction = transactionList.get(transactionList.size()-1);

        Date issueDate = latestTransaction.getCreatedAt();

        Integer year=issueDate.getYear()+1900;

        long milliSecondTime = Math.abs(System.currentTimeMillis() - issueDate.getTime());
        long no_of_days_issued = TimeUnit.DAYS.convert(milliSecondTime,TimeUnit.MILLISECONDS);


        int fineAmount = 0;
        if(no_of_days_issued>15){
            fineAmount = (int) ((no_of_days_issued - 15)*5);
        }

        book.setIsAvailable(Boolean.TRUE);
        card.setNoOfBooksIssued(card.getNoOfBooksIssued()-1);

        Transaction transaction = new Transaction(TransactionStatus.SUCCESS,TransactionType.RETURN,fineAmount);

        transaction.setBook(book);
        transaction.setLibraryCard(card);


        Transaction newTransactionWithId = transactionRepository.save(transaction);

        book.getTransactionList().add(newTransactionWithId);
        card.getTransactionList().add(newTransactionWithId);

        //Saving the parents
        bookRepository.save(book);
        cardRepository.save(card);

        return "Book has successfully been returned";
    }


    public  double calculateTotalFinesCollectedIn2023() {

        List<Transaction> transactions = transactionRepository.findAll(); // You need to create a method to fetch all transactions

        double totalFines = 0;

        for (Transaction transaction : transactions) {
            Date transactionDate = transaction.getCreatedAt();
            Calendar calendar = Calendar.getInstance();
            int transactionYear = calendar.get(Calendar.YEAR);
            if (transactionYear == 2023) {
                totalFines += transaction.getFineAmount();
            }
        }

        return totalFines;
    }


}