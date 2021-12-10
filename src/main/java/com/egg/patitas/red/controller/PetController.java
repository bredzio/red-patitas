package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.Animal;
import com.egg.patitas.red.model.Pet;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.service.AnimalService;
import com.egg.patitas.red.service.PetService;
import com.egg.patitas.red.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    AnimalService animalService;

    @Autowired
    UserService userService;

    @GetMapping
    public ModelAndView showAll(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("pets");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("title", "Mascotas");
        mav.addObject("pets",petService.findAll());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createPet(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView("pet-form");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            //mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("pet", new Pet());
        mav.addObject("animals",animalService.findAll());
        String email=(String) session.getAttribute("email");
        mav.addObject("users", userService.findByEmail(email));
        mav.addObject("title", "Crear Mascota");
        mav.addObject("action", "save");
        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String name, @RequestParam MultipartFile photo, @RequestParam Animal animal, @RequestParam User user, RedirectAttributes attributes){
        try {
            petService.createPet(name, photo,animal, user); //pet.getAnimal()
        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/pets/create");
        }
        return new RedirectView("/pets");
    }

    @PostMapping("/delete/{id}")
    public RedirectView deletePet(@PathVariable Integer id , RedirectAttributes attributes)  {
        RedirectView redirectView = new RedirectView("/pets");
        try {
            petService.deletePet(id);
            attributes.addFlashAttribute("success","Se borro el animal");

        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());

        }

        return redirectView;
    }

    @PostMapping("/enabled/{id}")
    public RedirectView enabledPet(@PathVariable Integer id , RedirectAttributes attributes)  {
        RedirectView redirectView = new RedirectView("/pets");
        try {
            petService.enabledPet(id);
            attributes.addFlashAttribute("success","Se habilto el animal");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", e.getMessage());
        }
        return redirectView;
    }
}
