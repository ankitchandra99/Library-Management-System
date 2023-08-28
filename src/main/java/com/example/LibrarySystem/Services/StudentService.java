package com.example.LibrarySystem.Services;

import com.example.LibrarySystem.Enums.Department;
import com.example.LibrarySystem.Models.Student;
import com.example.LibrarySystem.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public String addStudent(Student student)throws Exception{

        if(student.getRollNo()!=null) {
            throw new Exception("Id should not be sent as a parameter");
        }
        studentRepository.save(student);//student entity get save here.
        return "Student has been added successfully";
    }

    public Department getDepartmentById(Integer rollNo)throws Exception{

        Optional<Student> optionalStudent = studentRepository.findById(rollNo);

        if(!optionalStudent.isPresent()) {
            throw new Exception("Roll No Entered is Invalid");
        }
        Student student = optionalStudent.get();

        return student.getDepartment();
    }

}
