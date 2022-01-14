package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
public class PrincipalController {

    @GetMapping
    public ModelAndView inicio(HttpServletRequest request, Principal principal){
        ModelAndView mav = new ModelAndView("index");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("contact", new Contact());

        }else{
            mav.addObject("contact", new Contact());
        }

        return mav;

    }
}
