package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.driver.models.CardStatus.DEACTIVATED;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    public int max_allowed_books;

    @Value("${books.max_allowed_days}")
    public int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    public int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {

        //conditions required for successful transaction of issue book:
        //1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not present");
        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");
        //3. number of books issued against the card is strictly less than max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");
        //If the transaction is successful, save the transaction to the list of transactions and return the id

        //Note that the error message should match exactly in all cases

        //check whether bookId and cardId already exist

        Card card = cardRepository5.findById(cardId).get();
        Book book = bookRepository5.findById(bookId).get();


        if(card !=null && book!=null)
        {
            if(book.isAvailable()==false)
            {
                throw new Exception("Book is either unavailable or not present");
            }
            if(card.getCardStatus()== DEACTIVATED)
            {
                throw new Exception("Card is invalid");
            }
            int currentNumberOfBooks = card.getBooks().size();

            if(currentNumberOfBooks < max_allowed_books-1)
            {
               card.getBooks().add(bookRepository5.findById(bookId).get());
               book.setAvailable(false);

               Transaction transaction = new Transaction();
               transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
               String transactionId = transaction.getTransactionId();
               book.getTransactions().add(transaction);
               return transactionId;

            }
            else
                throw new Exception("Book limit has reached for this card");



        }
       return null; //return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well

        Transaction returnBookTransaction  = null;

    // book made available and book removed from card

         Book book = bookRepository5.findById(bookId).get();

         Card card = cardRepository5.findById(cardId).get();
         List<Book> bookList = card.getBooks();


         // fine
        Date isdate=transaction.getTransactionDate();

        for(Book books : bookList)
        {
            if(books.getId()==bookId)
            {
                books.getTransactions();
            }
        }

        book.setAvailable(true);
        bookList.remove(book);

        return returnBookTransaction; //return the transaction after updating all details
    }
}
