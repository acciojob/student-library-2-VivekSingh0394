package com.driver.services;

import com.driver.models.Card;
import com.driver.models.CardStatus;
import com.driver.models.Student;
import com.driver.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;

    public Student getDetailsByEmail(String email){
        Student student = new Student();
          student = studentRepository4.findByEmailId(email);

        return student;
    }

    public Student getDetailsById(int id){
        Student student = new Student();
         student=studentRepository4.findById(id).get();
        return student;
    }

    public void createStudent(Student student){

        Card newCard = new Card();
        newCard.setCreatedOn(new Date());
        newCard.setUpdatedOn(new Date());
        newCard.setCardStatus(CardStatus.ACTIVATED);
        newCard.setStudent(student);
        student.setCard(newCard);
        studentRepository4.save(student);

    }

    public void updateStudent(Student student){
      // int id = student.getId();
      // Student student1 = studentRepository4.findById(id).get();
        studentRepository4.save(student);
    }

    public void deleteStudent(int id){
        //Delete student and deactivate corresponding card


          cardService4.deactivateCard(id);
        studentRepository4.deleteById(id);

    }
}