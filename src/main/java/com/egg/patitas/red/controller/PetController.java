package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.Pet;
import com.egg.patitas.red.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import javax.validation.Valid;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/create")
    public ModelAndView createPet(){
        ModelAndView mav= new ModelAndView("pet-form");
        return mav;
    }

    @PostMapping("/create/save")
    public RedirectView save(ModelAndView mav, @Valid @RequestBody Pet pet, MultipartFile photo){

        try {
               petService.createPet(pet.getName(), photo, pet.getAnimal());

        }catch (Exception e){
                mav.addObject("ErrorCreatePet", e.getMessage());
                mav.addObject("name", pet.getName());
                mav.addObject("photo", photo);
                mav.addObject("animal", pet.getAnimal());
                return new RedirectView("/create");
        }

        return new RedirectView("/");
    }

    @GetMapping("/showAll")
    public ModelAndView showAll() throws Exception {
        ModelAndView mav = new ModelAndView("pets");
        mav.addObject("pets", petService.listPet());
        return mav;
    }
}
