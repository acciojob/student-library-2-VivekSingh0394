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
import java.util.concurrent.TimeUnit;

import static com.driver.models.CardStatus.ACTIVATED;
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
        Transaction transaction = new Transaction();// whether issued or not transaction will happen
        transaction.setBook(book);
        transaction.setCard(card);
        transaction.setTransactionDate(new Date());
        transaction.setIssueOperation(true);


        if(book == null || book.isAvailable()==false)
     {
       transaction.setTransactionStatus(TransactionStatus.FAILED);
       transactionRepository5.save(transaction);
         throw new Exception("Book is either unavailable or not present");
     }
        if(card == null || card.getCardStatus().equals(CardStatus.DEACTIVATED))
        {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository5.save(transaction);
            throw new Exception("Card is invalid");
        }
        if(card.getBooks().size()>=max_allowed_books)
        {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository5.save(transaction);
            throw new Exception("Book limit has reached for this card");

        }
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        // make book unable
         book.setAvailable(false);
        // set card with book
        book.setCard(card);


        List<Book> bookList = card.getBooks();
        bookList.add(book);
        card.setBooks(bookList);
        bookRepository5.updateBook(book);
        cardRepository5.save(card);

        transactionRepository5.save(transaction);

         String id = null;
       id = transaction.getTransactionId();
       return id;


     //  return null; //return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well

        Transaction returnBookTransaction  = new Transaction();

    // book made available and book removed from card

         Book book = bookRepository5.findById(bookId).get();

         Card card = cardRepository5.findById(cardId).get();

         List<Book> bookList = card.getBooks();


         // fine
        Date issuedate=transaction.getTransactionDate();
//        int fine =0;
//
//        long numberOfDays = (System.currentTimeMillis() - issuedate.getTime())/(1000*60*60*24);
//        if(numberOfDays > getMax_allowed_days)
//       fine = (int)numberOfDays*fine_per_day;
        long timeIssuetime = Math.abs(System.currentTimeMillis() - issuedate.getTime());

        long no_of_days_passed = TimeUnit.DAYS.convert(timeIssuetime, TimeUnit.MILLISECONDS);

        int fine = 0;
        if(no_of_days_passed > getMax_allowed_days){
            fine = (int)((no_of_days_passed - getMax_allowed_days) * fine_per_day);
        }

        // setting book attributes
        book.setAvailable(true);
        book.setCard(null);

       bookList.remove(book);
        bookRepository5.updateBook(book);
        cardRepository5.save(card);

        // setting return transaction attributes

        returnBookTransaction.setFineAmount(fine);
        returnBookTransaction.setTransactionDate(new Date());
        returnBookTransaction.setIssueOperation(false);
        returnBookTransaction.setBook(book);
        returnBookTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        returnBookTransaction.setCard(card);

        transactionRepository5.save(returnBookTransaction);



        return returnBookTransaction; //return the transaction after updating all details
    }
}
