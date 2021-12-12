package com.egg.patitas.red.service;

import com.egg.patitas.red.email.EmailSend;
import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.Contact;
import com.egg.patitas.red.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final EmailSend emailSend;

    @Transactional
    public void createContact(Contact dto) throws MessagingException, EmailExistException {
        contactRepository.save(buildContact(dto));
    }

    @Transactional
    public Contact buildContact(Contact dto) throws MessagingException, EmailExistException {
        Contact contact = new Contact();

        contact.setName(dto.getName());
        contact.setEmail(dto.getEmail());
        contact.setMessage(dto.getMessage());

        emailSend.sendContactEmail(contact);
        emailSend.sendThanksContactEmail(contact);

        return contact;
    }


}
