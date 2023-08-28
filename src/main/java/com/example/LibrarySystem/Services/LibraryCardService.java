package com.example.LibrarySystem.Services;

import com.example.LibrarySystem.Enums.CardStatus;
import com.example.LibrarySystem.Models.Book;
import com.example.LibrarySystem.Models.LibraryCard;
import com.example.LibrarySystem.Models.Student;
import com.example.LibrarySystem.Repositories.CardRepository;
import com.example.LibrarySystem.Repositories.StudentRepository;
import com.example.LibrarySystem.ResponseDto.BookResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryCardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private StudentRepository studentRepository;


    public String addCard(LibraryCard libraryCard){
        cardRepository.save(libraryCard);
        return "Card has successfully been added to the database";
    }

    public String associateToStudent(Integer cardNo,Integer rollNo)throws Exception{

        //Student should exist
        if(!studentRepository.existsById(rollNo)){
            throw new Exception("Student Id is Invalid");
        }

        //Card should also exist
        if(!cardRepository.existsById(cardNo)){
            throw new Exception("Card No is Invalid");
        }

        //I need to update those FK variables :

        Optional<Student> optional = studentRepository.findById(rollNo);
        Student studentObj = optional.get();


        Optional<LibraryCard> optionalLibraryCard = cardRepository.findById(cardNo);
        LibraryCard libraryCard = optionalLibraryCard.get();



        //Set the studentObj object in card object
        libraryCard.setStudent(studentObj);

        //Since its a bidirectional mapping
        //In the studentObj object also we need to set the libraryCard Object
        studentObj.setLibraryCard(libraryCard);

        //any object that has been updated should be saved ???---> Correct Y/N

        //Save both of them : since both were updated

        studentRepository.save(studentObj);

        //CardRepository saving can be skipped bcz
        //studentObj will automatically trigger for the cardRepository Save function

        return "Student and card saved successfully";

    }

    public List<Student> getBookListByStatus(CardStatus status) {
        List<LibraryCard> libraryCardList = cardRepository.findBookByStatus(Active);



    }
}
