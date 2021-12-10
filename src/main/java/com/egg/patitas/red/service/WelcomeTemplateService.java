package com.egg.patitas.red.service;

import com.egg.patitas.red.exception.EmailExistException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WelcomeTemplateService {
    @Autowired
    private Configuration config;

    @Autowired
    private MessageSource messageSource;

    public String setTemplate(String name, String surname, String link) throws EmailExistException {
        Map<String, Object> model = new HashMap<>();

        model.put("name", name);
        model.put("surname", surname);
        model.put("link", link);

        try {

            Template t = config.getTemplate("email-template.ftl");

            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        }catch (TemplateException | IOException e){

            throw new EmailExistException(e.getMessage());

        }
    }

    public String getSubject(){

        return "Por favor confirme su cuenta";
    }
}
