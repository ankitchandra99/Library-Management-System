package com.example.LibrarySystem.Services;


import com.example.LibrarySystem.Models.Author;
import com.example.LibrarySystem.Repositories.AuthorRepository;
import com.example.LibrarySystem.RequestDto.UpdateNameAndPenNameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;


    public String addAuthor(Author author)throws Exception{

        //Validation Checks
        if(author.getAuthorId()!=null){
            throw new Exception("Author Id should not be sent as a parameter");
        }

        authorRepository.save(author);
        return "Author has been successfully to the db";
    }

    public String updateNameAndPenName(UpdateNameAndPenNameRequest request)throws Exception{


        Optional<Author> authorOptional = authorRepository.findById(request.getAuthorId());

        if(!authorOptional.isPresent()){
            throw new Exception("AuthorId is Invalid");
        }

        Author author = authorOptional.get();

        author.setName(request.getNewName());
        author.setPenName(request.getNewPenName());

        authorRepository.save(author);

        return "Author Name and PenName has been updated";
    }

    public Author getAuthorById(Integer authorId){

        Author author = authorRepository.findById(authorId).get();
        return author;

    }

}
