package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.Contact;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.security.SecurityConstant;
import com.egg.patitas.red.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ContactService contactService;

    @GetMapping("/selectPost")
    public ModelAndView home(HttpSession session) {
        ModelAndView mav = new ModelAndView("selectPost");
        mav.addObject("name", session.getAttribute("name"));
        return mav;
    }

    @PostMapping("/createContact")
    public RedirectView createContact(@Valid @ModelAttribute Contact contact, BindingResult bindingResult, RedirectAttributes attributes, HttpServletRequest request) throws Exception {
        RedirectView redirectView = new RedirectView("/");

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                attributes.addFlashAttribute("error",error.getDefaultMessage());
                attributes.addFlashAttribute("contact", contact);
            }

            return redirectView;
        }

        try{
            contactService.createContact(contact);
            attributes.addFlashAttribute("exito", "Mensaje enviado exitosamente");
            attributes.addFlashAttribute("contact", contact);
            redirectView.setUrl("/");
        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("contact", contact);
            redirectView.setUrl("/");
        }

        return redirectView;
    }


    @GetMapping("/nosotros")
    public ModelAndView nosotros() {
        ModelAndView mav = new ModelAndView("quienes-somos");
        return mav;
    }

    @GetMapping("/ayuda")
    public ModelAndView ayuda() {
        ModelAndView mav = new ModelAndView("como-publicar");
        return mav;
    }


    @GetMapping("/create")
    public ModelAndView crear(HttpSession session) {
        ModelAndView mav = new ModelAndView("create");
        mav.addObject("name", session.getAttribute("name"));
        return mav;
    }

    @GetMapping("/dashboard")
    @PreAuthorize(SecurityConstant.ADMIN)
    public ModelAndView dashboard(HttpSession session) {
        ModelAndView mav = new ModelAndView("dashboard");
        mav.addObject("name", session.getAttribute("name"));
        return mav;
    }
}
