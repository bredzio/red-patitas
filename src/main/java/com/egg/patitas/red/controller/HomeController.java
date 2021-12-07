package com.egg.patitas.red.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("/")
    public ModelAndView home(Principal principal) {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @GetMapping("/nosotros")
    public ModelAndView nosotros(Principal principal) {
        ModelAndView mav = new ModelAndView("quienes-somos");
        return mav;
    }
}
