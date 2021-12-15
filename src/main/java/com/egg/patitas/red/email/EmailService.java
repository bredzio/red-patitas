package com.egg.patitas.red.email;

import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.Contact;
import com.egg.patitas.red.service.ContactTemplateService;
import com.egg.patitas.red.service.PasswordTemplateService;
import com.egg.patitas.red.service.ThanksTemplateService;
import com.egg.patitas.red.service.WelcomeTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSend{
    private final JavaMailSender mailSender;
    private final WelcomeTemplateService welcomeTemplateService;
    private final ContactTemplateService contactTemplateService;
    private final ThanksTemplateService thanksTemplateService;
    private final PasswordTemplateService passwordTemplateService;

    private final String ORGANIZATION_EMAIL= "teamhuellapp@gmail.com";

    @Override
    @Async
    public void send(String to, String email, String subject) throws MessagingException {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(ORGANIZATION_EMAIL);
            mailSender.send(mimeMessage);

    }

    @Override
    @Async
    public void sendWelcomeEmail(String to, String name, String surname,String link) throws EmailExistException, MessagingException {
        String subject = welcomeTemplateService.getSubject();
        String template = welcomeTemplateService.setTemplate(name, surname, link);
        send(to, template, subject);
    }

    @Override
    @Async
    public void sendContactEmail(Contact contact) throws EmailExistException, MessagingException {
        String subject = contactTemplateService.getSubject();
        String template = contactTemplateService.setTemplate(contact);
        send(ORGANIZATION_EMAIL, template, subject);
    }

    @Override
    @Async
    public void sendThanksContactEmail(Contact contact) throws EmailExistException, MessagingException {
        String subject = thanksTemplateService.getSubject();
        String template = thanksTemplateService.setTemplate(contact);
        send(contact.getEmail(), template, subject);
    }

    @Override
    @Async
    public void sendPasswordEmail(String to, String name,String link) throws EmailExistException, MessagingException {
        String subject = passwordTemplateService.getSubject();
        String template = passwordTemplateService.setTemplate(name, link);
        send(to, template, subject);
    }
}

