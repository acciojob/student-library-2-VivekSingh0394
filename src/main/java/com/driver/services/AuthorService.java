package com.driver.services;

import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.models.Transaction;
import com.driver.models.TransactionStatus;
import com.driver.repositories.AuthorRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {


    @Autowired
    AuthorRepository authorRepository1;
    @Autowired
    TransactionRepository transactionRepository;

    public void create(Author author){

        authorRepository1.save(author);
    }
    public String mostLovedAuthor()
    {
        // list of all authors in Db
        List<Author> authorList = authorRepository1.findAll();
          String authorName ="";
          int authorid=-1;
        int maxIssued=-1;
        int numberoftimesIssued=0;
         for(Author author:authorList)
         {             // books written by this author
             numberoftimesIssued=0;
             List<Book> bookList = author.getBooksWritten();
             for(Book book : bookList)
             {
                 // each book transaction successful status history will give how many times it was issued
                 List<Transaction> bookTransactionhistory = book.getTransactions();
                 for(Transaction transaction : bookTransactionhistory)
                 {
                     if(transaction.getTransactionStatus().equals(TransactionStatus.SUCCESSFUL))
                     {
                         numberoftimesIssued++;

                     }
                 }
             }
             if(numberoftimesIssued > maxIssued)
             {
                 maxIssued =numberoftimesIssued;
               //  authorName = author.getName();
                 authorid =author.getId();
             }
         }
         authorName = authorRepository1.findById(authorid).get().getName();
         return authorName;

    }
}
