package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.User;
import com.egg.patitas.red.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;;
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
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error,@RequestParam(required = false) String success, @RequestParam(required = false) String logout, Principal principal) {
        ModelAndView mav = new ModelAndView("login");

        if (error != null) {
            mav.addObject("error", "Correo o contraseña inválida");
        }

        if (logout != null) {
            mav.addObject("logout", "Ha salido correctamente de la plataforma");
        }

        if (success != null) {
            mav.addObject("exito", "Por favor verifique su correo electrónico");
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

        if (principal != null) {
            mav.setViewName("redirect:/");
        }

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("user", flashMap.get("user"));

        }else{
            mav.addObject("user", new User());
        }


        return mav;
    }

    @PostMapping("/register")
    public RedirectView registerUser(@Valid @ModelAttribute User user, BindingResult bindingResult, RedirectAttributes attributes, HttpServletRequest request) throws Exception {
        RedirectView redirectView = new RedirectView("auth/login");

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors ) {
                attributes.addFlashAttribute("error",error.getDefaultMessage());
                attributes.addFlashAttribute("user", user);
            }

            redirectView.setUrl("/auth/signup");
            return redirectView;
        }

        try{
            userService.createUser(user);
            redirectView.setUrl("/auth/login?success=true");
        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("user", user);
            redirectView.setUrl("/auth/signup");
        }

        return redirectView;
    }

    @GetMapping("/register/confirm")
    public ModelAndView login(@RequestParam("token") String token) {
        ModelAndView mav = new ModelAndView("login");
        try {
            mav.addObject("exito", userService.confirmToken(token));

        }catch(Exception e){
               mav.addObject("error",e.getMessage());
        }
        return mav;
    }
}
