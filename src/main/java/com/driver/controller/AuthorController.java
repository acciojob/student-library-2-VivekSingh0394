package com.driver.controller;

import com.driver.RequestDto.AuthorRequestDto;
import com.driver.models.Author;
import com.driver.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Add required annotations
@RestController
@RequestMapping("author")
public class AuthorController {
   @Autowired
    AuthorService authorService;
    //Write createAuthor API with required annotations
    @PostMapping("/createAuthor")
    public String createAuthor(@RequestBody() Author author)
    {
     authorService.create(author);
     return "Success";
    }
}
