package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Author;
import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Genre;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Blob;
import java.util.List;
import java.util.Random;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    private JavaMailSender emailSender;


    public Book findById(Long id) {
        return bookRepository.getOne(id);
    }

    public List<Book> findAll( ) {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public Book updateBook(Book bookDetails, Long id) {
        Book book = bookRepository.findBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setTitle(bookDetails.getTitle());
        book.setGenre(bookDetails.getGenre());
        book.setPages(bookDetails.getPages());
        book.setDescription(bookDetails.getDescription());
        book.setQuantity(bookDetails.getQuantity());
        book.setCover(bookDetails.getCover());
        return bookRepository.save(book);
    }


    public boolean reservation(Book book) {
        Integer quantity = book.getQuantity();
        if (quantity <= 0) {
            return false;
        } else {
            quantity = quantity - 1;
            book.setQuantity(quantity);
            bookRepository.save(book);

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = null;
            if (principal instanceof UserDetails) {
                username = ((UserDetails)principal).getUsername();
            } else {
                username = principal.toString();
            }

            Random rand = new Random();
            // Generating random integers in range 0 to 9999
            int int1 = rand.nextInt(10000);

            sendSimpleMessage(username, "Your reservation confirmed",
                    "Dear customer," + "\n\nThank you for choosing Online Library. Your request number is #" + int1 + "!" +
                            "\nWe are pleased to confirm your reservation. You have 24 hours to pick up the selected book." + "\n\nBook pick-up address: Krisjana Barona iela 14, Riga, LV-1050" +
                            "\nOpening hours - I-V: 08:00 - 20:00" + "\nContacts: phone - +371 26899876; e-mail: onlinelibraryteam@gmail.com" + "\n\n\n\n\nBest regards," + "\nOnlineLibrary administration.");
            return true;
        }
    }



    private void sendSimpleMessage(
            String to, String subject, String text) {



        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("onlinelibraryteam@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }




    public List<Book> search(String keyword) {
        if (keyword != null) {
            return bookRepository.search(keyword);
        }
        return bookRepository.findAll();
    }


}