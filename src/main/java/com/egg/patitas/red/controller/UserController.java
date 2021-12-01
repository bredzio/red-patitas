package com.egg.patitas.red.controller;

import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ModelAndView showAll(){
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users", userService.findAll());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createPost() {
        ModelAndView mav = new ModelAndView("user-form");

        mav.addObject("user", new User());
        mav.addObject("title", "Crear User");
        mav.addObject("action", "save");
        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam @RequestBody User user) throws EmailExistException {
        userService.createUser(user);
        return new RedirectView("/users");
    }


}
