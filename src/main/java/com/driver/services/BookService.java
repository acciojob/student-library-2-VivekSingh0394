package com.driver.services;

import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.repositories.AuthorRepository;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorService authorService;

    public void createBook(Book book)
    {
        int authorId = book.getAuthor().getId();
        Author author = authorRepository.findById(authorId).get();

       List<Book> books= author.getBooksWritten();
       books.add(book);

       book.setAuthor(author);


        book.setAvailable(true);

    bookRepository2.save(book);
        authorRepository.save(author);

    }

    public List<Book> getBooks(String genre, boolean available, String author){



        // If genre=”X”, availability = true, and author=null; we require the list of all books
        // which are available and have genre “X”. Note that these books can be written by any author.
        // ii) If genre=”Y”, availability = false, and author=”A”; we require the list of all books
        // which are written by author “A”, have genre “Y”, and are currently unavailable.
        // Return success message wrapped in a ResponseEntity object Controller Name - getBooks!
        if(genre!=null && available==true && author!=null)
            return bookRepository2.findBooksByGenreAuthor(genre,author,available);

        else if(author!=null)
            return bookRepository2.findBooksByAuthor(author,available);
        else if(genre!=null)
            return bookRepository2.findBooksByGenre(genre,available);
      else
          return bookRepository2.findByAvailability(available);




    }
}
