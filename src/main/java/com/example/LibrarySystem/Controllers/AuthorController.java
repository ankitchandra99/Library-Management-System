package com.example.LibrarySystem.Controllers;


import com.example.LibrarySystem.Models.Author;
import com.example.LibrarySystem.RequestDto.UpdateNameAndPenNameRequest;
import com.example.LibrarySystem.Services.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
@Slf4j
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @PostMapping("/add")
    public ResponseEntity addAuthor(@RequestBody Author author){

        try{

            String result = authorService.addAuthor(author);
            return new ResponseEntity(result, HttpStatus.OK);

        }
        catch (Exception e){
            log.error("Author couldnt be added to the db {}",e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateNameAndPenName")
    //@RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String updateAuthorNameAndPenName(@RequestBody UpdateNameAndPenNameRequest updateNameAndPenNameRequest){


        //@RequestBody Author author

        //1 endpoint has become long
        //Exposed in the URL itself

        try{
            String result = authorService.updateNameAndPenName(updateNameAndPenNameRequest);
            return result;

        }catch (Exception e){
            return "Author Id is invalid"+e.getMessage();
        }

    }

    //@RequestMapping(value = "/ex/foos", method = RequestMethod.GET)
    @GetMapping("/getAuthor")
    public Author getAuthor(@RequestParam("authorId")Integer authorId){

        return authorService.getAuthorById(authorId);

    }



}
