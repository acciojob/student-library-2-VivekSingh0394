package com.driver.controller;

import com.driver.models.Book;
import com.driver.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Add required annotations
@RestController
@RequestMapping("book")
public class BookController {
//Create a Book: POST /book/ Pass the Book object as request body Return
// success message “Success” wrapped in a ResponseEntity object Controller Name - createBook

    //Write createBook API with required annotations
@Autowired
    BookService bookService;
    //Add required annotations
    @PostMapping("/createBook")
    public String createBook(@RequestBody() Book book)
    {
        bookService.createBook(book);
        return "Success";
    }
    public ResponseEntity getBooks(@RequestParam(value = "genre", required = false) String genre,
                                   @RequestParam(value = "available", required = false, defaultValue = "false") boolean available,
                                   @RequestParam(value = "author", required = false) String author){

        List<Book> bookList = null; //find the elements of the list by yourself

        return new ResponseEntity<>(bookList, HttpStatus.OK);

    }
}
