package com.driver.services;

import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.repositories.AuthorRepository;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;
    @Autowired
    AuthorRepository authorRepository;

    public void createBook(Book book)
    {

        Author author = book.getAuthor();

        // we have got author now we need to set the author for the book
        book.setAuthor(author);

        // since this book needs to be added in the list of books for this author

        List<Book> currentListOfBooks = author.getBooksWritten();
        currentListOfBooks.add(book);
        author.setBooksWritten(currentListOfBooks);
        authorRepository.save(author);

        bookRepository2.save(book);
    }

    public List<Book> getBooks(String genre, boolean available, String author){

        List<Book> books = null; //find the elements of the list by yourself

        // If genre=”X”, availability = true, and author=null; we require the list of all books
        // which are available and have genre “X”. Note that these books can be written by any author.
        // ii) If genre=”Y”, availability = false, and author=”A”; we require the list of all books
        // which are written by author “A”, have genre “Y”, and are currently unavailable.
        // Return success message wrapped in a ResponseEntity object Controller Name - getBooks
         if(genre.length()>0 && available==true && author.length()>0)
         {
             books = bookRepository2.findBooksByGenreAuthor(genre,author,available);
         }
        else if(author.length()==0)
        {
            books = bookRepository2.findBooksByGenre(genre,available);
        }
        else if(available == false)
        {
            books = bookRepository2.findBooksByGenreAuthor(genre,author,false);
        }
        else if(genre.length()==0)
         {
             books = bookRepository2.findBooksByAuthor(author,available);
         }

        return books;
    }
}
