package com.example.LibrarySystem.Repositories;

import com.example.LibrarySystem.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {

//here it is student (Entity)object,extending JPA interface
}
