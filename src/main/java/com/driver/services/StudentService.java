package com.driver.services;

import com.driver.Convertor.BookConvertor;
import com.driver.RequestDto.StudentRequestDto;
import com.driver.ResponseDtos.BookResponseDto;
import com.driver.models.*;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CardRepository cardRepository;
    @Value("${books.max_allowed_days}")
    public int getMax_allowed_days;
    @Value("${books.fine.per_day}")
    public int fine_per_day;

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

    public void createStudent(StudentRequestDto studentRequestDto){

        Student student = new Student();
        student.setAge(studentRequestDto.getAge());
        student.setCountry(studentRequestDto.getCountry());
        student.setEmailId(studentRequestDto.getEmail());
        student.setName(studentRequestDto.getName());

        Card newCard = new Card();

        newCard.setCardStatus(CardStatus.ACTIVATED);

        newCard.setStudent(student); //For that new foreign key column

        //For that bidirectional relation
        student.setCard(newCard);

        //cardRepository.save(newCard) ;
        studentRepository4.save(student);
      //will automatically be taken of.
      //  cardService4.createAndReturn(student);

    }

    public void updateStudent(Student student){
     studentRepository4.updateStudentDetails(student);
    }

    public void deleteStudent(int id){
        //Delete student and deactivate corresponding card


          cardService4.deactivateCard(id);
        studentRepository4.deleteCustom(id);

    }
    public List<BookResponseDto> getListOfbooksWhichTheStudentHasNotRead(int studentId)
    {
        Student student = studentRepository4.findById(studentId).get();
        Card card = student.getCard();
        // list of books which the student has read
        List<Book> bookList = card.getBooks();
        // find all books
        List<Book> allBooks = bookRepository.findAll();

        // now the books which student has not read will be added in this list
         List<BookResponseDto> booksToBeRead = new ArrayList<>();
         // comparison
        boolean check =false;
        for(Book book:allBooks)
        {
            int id= book.getId();
            check =false;
            for(Book book1:bookList)
            {
                if(book1.getId()==id)
                {
                    check =true;
                    break;
                }
            }
            if(check ==false)
            {
                String name = book.getName();
                Genre genre = book.getGenre();
                String author=book.getAuthor().getName();

              BookResponseDto bookResponseDto = new BookResponseDto();
              bookResponseDto.setBookName(name);
              bookResponseDto.setGenre(genre);
              bookResponseDto.setAuthorName(author);
             booksToBeRead.add(bookResponseDto);
            }
        }
        return booksToBeRead;

    }
    public String studentwhoHasPaidMaxFine()
    {        int maxFine = -1;
      //  int fine =0;
        String studentName="";
        // get all students
        List<Student> studentList = studentRepository4.findAll();
        for(Student student : studentList)
        {
            Card card = student.getCard();
            // for each card there is a list of books
            List<Book> bookList = card.getBooks();
            // each book has transaction list
            int fineofthisstudent =0;
            for(Book book:bookList)
            {
                List<Transaction> transactionList = book.getTransactions();
                int fine =0;
                for(Transaction transaction:transactionList)
                {
                    if(transaction.getCard().getId()== card.getId())
                    {
                        Date issueDate = transaction.getTransactionDate();
                        //long numberOfDaysElapsed = (System.currentTimeMillis() - issueDate.getTime())/(1000*60*60*24);
                        long numberOfDaysElapsed = (System.currentTimeMillis() - issueDate.getTime()) / (1000 * 60);
                        if (numberOfDaysElapsed > getMax_allowed_days) {
                            fine = (int) (numberOfDaysElapsed - getMax_allowed_days) * fine_per_day;
                        }
                        fineofthisstudent += fine;
                    }
                }
            }
            if(fineofthisstudent > maxFine)
            {
                maxFine=fineofthisstudent;
                studentName=student.getName();
            }

        }
        return  studentName+" rs "+String.valueOf(maxFine);
    }
}
