package com.example.LibrarySystem.Controllers;


import com.example.LibrarySystem.Enums.CardStatus;
import com.example.LibrarySystem.Models.LibraryCard;
import com.example.LibrarySystem.Models.Student;
import com.example.LibrarySystem.ResponseDto.BookResponseDto;
import com.example.LibrarySystem.Services.LibraryCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@Slf4j
public class CardController {

    @Autowired
    private LibraryCardService cardService;


    @PostMapping("/create")
    public String addCard(@RequestBody LibraryCard card){

        return cardService.addCard(card);

    }

    @PutMapping("/issueToStudent")
    public ResponseEntity issueToStudent(@RequestParam Integer cardId,@RequestParam Integer rollNo){

        try{
            String result =  cardService.associateToStudent(cardId,rollNo);
            return new ResponseEntity(result,HttpStatus.OK);
        }catch (Exception e){
            log.error("Error in associating card to student",e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

    }
  /*  @GetMapping("/studentStatusNotActive")
    public ResponseEntity statusNotActive(@RequestParam("status") CardStatus status){
        List<Student> responseDtoList = cardService.getBookListByStatus(status);
        return new ResponseEntity(responseDtoList,HttpStatus.OK);
    }*/
}
