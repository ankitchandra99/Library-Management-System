package com.example.LibrarySystem.Models;

import com.example.LibrarySystem.Enums.Department;
import com.example.LibrarySystem.Enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



    @Entity
    @Table(name="student_details")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Student {

        @Id //Used for saying it's a primary key
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer rollNo;

        private String name;

        private Integer age;

        @Enumerated(value = EnumType.STRING)
        private Gender gender; //This gender variable is of user defined datatype : this contains only 2 values : MALE,FEMALE

        @Enumerated(value = EnumType.STRING)
        private Department department;

        @Column(unique = true)
        private String emailId;

        //Bidirectional Mapping
        @OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
        private LibraryCard libraryCard;
}
