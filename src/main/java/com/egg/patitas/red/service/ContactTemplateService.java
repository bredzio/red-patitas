package com.egg.patitas.red.service;

import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.Contact;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class ContactTemplateService {
    private final Configuration config;
    private final MessageSource messageSource;

    public String setTemplate(Contact contact) throws EmailExistException {
        Map<String, Object> model = new HashMap<>();

        model.put("name", contact.getName());
        model.put("email", contact.getEmail());
        model.put("message", contact.getMessage());

        try {

            Template t = config.getTemplate("contact-template.ftl");

            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        }catch (TemplateException | IOException e){

            throw new EmailExistException(e.getMessage());

        }
    }

    public String getSubject(){
        return "Ha recibido un nuevo mensaje";
    }
}
