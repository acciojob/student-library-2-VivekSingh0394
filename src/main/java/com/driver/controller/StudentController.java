package com.driver.controller;

import com.driver.RequestDto.StudentRequestDto;
import com.driver.ResponseDtos.BookResponseDto;
import com.driver.models.Student;
import com.driver.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Add required annotations
@RestController
@RequestMapping("/student")
public class StudentController {

    //Add required annotations
    @Autowired
    StudentService studentService;
    @GetMapping("/getStudentByEmail")
    public ResponseEntity<String> getStudentByEmail(@RequestParam("email") String email){
       Student student = studentService.getDetailsByEmail(email);
        return new ResponseEntity<>("Student details printed successfully ", HttpStatus.OK);
    }

    //Add required annotations
    @GetMapping("/getStudentById")
    public ResponseEntity<String> getStudentById(@RequestParam("id") int id){
        Student student = studentService.getDetailsById(id);
        return new ResponseEntity<>("Student details printed successfully ", HttpStatus.OK);
    }

    //Add required annotations
    @PostMapping("/createStudent")
    public ResponseEntity<String> createStudent(@RequestBody StudentRequestDto student){
         studentService.createStudent(student);
        return new ResponseEntity<>("the student is successfully added to the system", HttpStatus.CREATED);
    }

    //Add required annotations
    @PutMapping("/updateStudent")
    public ResponseEntity<String> updateStudent(@RequestBody Student student){
     studentService.updateStudent(student);
        return new ResponseEntity<>("student is updated", HttpStatus.ACCEPTED);
    }

    //Add required
    @DeleteMapping("/deleteStudent")
    public ResponseEntity<String> deleteStudent(@RequestParam("id") int id){
    studentService.deleteStudent(id);
        return new ResponseEntity<>("student is deleted", HttpStatus.ACCEPTED);
    }
    @GetMapping("get_books_which_student_has_not_read")
    public List<BookResponseDto> getlistofbooks(@RequestParam("id")int id)
    {
        return studentService.getListOfbooksWhichTheStudentHasNotRead(id);
    }
    @GetMapping("/student_who has_paid_max_fine")
    public String studentwhohasPaidmaxFine()
    {
        return studentService.studentwhoHasPaidMaxFine();
    }

}
