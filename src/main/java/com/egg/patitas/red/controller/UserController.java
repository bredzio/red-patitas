package com.egg.patitas.red.controller;

import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.security.SecurityConstant;
import com.egg.patitas.red.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping
    public ModelAndView showAll(){
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users", userService.findAll());
        mav.addObject("title", "Users");
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createUser(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("user-form");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("error", flashMap.get("error"));
        }else{
            mav.addObject("user", new User());
            mav.addObject("title", "Nuevo usuario");
            mav.addObject("action", "save");
        }

        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@Valid @ModelAttribute User user,  BindingResult bindingResult, RedirectAttributes attributes) throws EmailExistException {
        RedirectView redirectView = new RedirectView("/users");

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                attributes.addFlashAttribute("error",error.getDefaultMessage());
                attributes.addFlashAttribute("user", user);
            }

            redirectView.setUrl("/users/create");
            return redirectView;
        }
        try{
            userService.createUser(user);
        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("user", user);
            redirectView.setUrl("/users/create");
        }

        return new RedirectView("/users");
    }

    @GetMapping("/edit/{email}")
    @PreAuthorize(SecurityConstant.ADMIN_OR_USERAUTH)
    public ModelAndView editUser(@PathVariable String email) {
        ModelAndView mav = new ModelAndView("user-form");
        mav.addObject("user", userService.findByEmail(email));
        mav.addObject("title", "Editar Perfil");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@ModelAttribute User user, RedirectAttributes attributes) {
        userService.edit(user.getId(), user.getName(),user.getLastname(),user.getEmail(),user.getPassword());
        return new RedirectView("/users");
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        userService.delete(id);
        return new RedirectView("/users");
    }

    @PostMapping("/enabled/{id}")
    public RedirectView enabled(@PathVariable Integer id) {
        userService.enabled(id);
        return new RedirectView("/users");
    }

}
