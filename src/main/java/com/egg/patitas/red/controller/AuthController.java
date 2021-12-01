package com.egg.patitas.red.controller;

import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {
        ModelAndView mav = new ModelAndView("login");

        if (error != null) {
            mav.addObject("error", "Correo o contraseña inválida");
        }

        if (logout != null) {
            mav.addObject("logout", "Ha salido correctamente de la plataforma");
        }

        if (principal != null) {
            mav.setViewName("redirect:/");
        }

        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView signup(HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("signup");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("name", flashMap.get("name"));
            mav.addObject("lastname", flashMap.get("lastname"));
            mav.addObject("email", flashMap.get("email"));
            mav.addObject("password", flashMap.get("password"));

        }

        if (principal != null) {
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    @PostMapping("/register")
    public RedirectView registerUser(@ModelAttribute User user, RedirectAttributes attributes, HttpServletRequest request) throws Exception {
        RedirectView redirectView = new RedirectView("/login");
        try{
            userService.createUser(user);
            attributes.addFlashAttribute("exito", "SE HA REGISTRADO CON ÉXITO.");
        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("name", user.getName());
            attributes.addFlashAttribute("lastname", user.getLastname());
            attributes.addFlashAttribute("email", user.getEmail());
            attributes.addFlashAttribute("password", user.getPassword());
            redirectView.setUrl("/signup");
        }

        return redirectView;
    }





}
