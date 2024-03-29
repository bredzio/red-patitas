package com.egg.patitas.red.service;

import com.egg.patitas.red.exception.EmailExistException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class PasswordTemplateService {
    private final Configuration config;
    private final String SUBJECT="Restablecer contraseña | Huellapp";

    public String setTemplate(String name, String link) throws EmailExistException {
        Map<String, Object> model = new HashMap<>();

        model.put("name", name);
        model.put("link", link);

        try {

            Template t = config.getTemplate("password-template.ftl");

            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        }catch (TemplateException | IOException e){

            throw new EmailExistException(e.getMessage());

        }
    }

    public String getSubject(){
        return SUBJECT;
    }
}
