package com.egg.patitas.red.email;

import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.Contact;

import javax.mail.MessagingException;

public interface EmailSend {
    void send(String to, String email,String subject) throws MessagingException;
    void sendWelcomeEmail(String to, String name, String surname, String link) throws EmailExistException, MessagingException;
    void sendContactEmail(String to, Contact contact) throws EmailExistException, MessagingException;
    }
