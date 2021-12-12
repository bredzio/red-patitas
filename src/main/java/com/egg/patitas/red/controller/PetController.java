package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.Animal;
import com.egg.patitas.red.model.Pet;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.repository.PetRepository;
import com.egg.patitas.red.service.AnimalService;
import com.egg.patitas.red.service.PetService;
import com.egg.patitas.red.service.PhotoService;
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
import java.util.Optional;

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
            attributes.addFlashAttribute("succes", "La mascota se creó con éxito!");
        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/pets/create");
        }
        return new RedirectView("/pets");
    }


//    @GetMapping("/{id}")
//    public ModelAndView viewPet() throws Exception {
//        ModelAndView mav = new ModelAndView("detailpet");
//        mav.addObject("pets",petService.listPet());
//        return mav;
//    }



    @GetMapping("/edit/{id}")
    public ModelAndView editPet(@PathVariable Integer id){

        ModelAndView mav = new ModelAndView("pet-edit");

        try{
            mav.addObject("pet", petService.findById(id));
            mav.addObject("title", "Editar Mascota");
            mav.addObject("animals",animalService.findAll());;
            mav.addObject("action", "edit/save");
            return  mav;
        }catch(Exception e){
            mav.addObject("error", e.getMessage());
            return  mav;
        }
 }


    @PostMapping("/edit/save")
    public RedirectView editSave(@RequestParam Integer id, @RequestParam String name ,@RequestParam Animal animal ,@RequestParam(required = false) MultipartFile photo, RedirectAttributes attributes) {
        try {
            if (photo.isEmpty() || photo == null) {
                petService.editPet(id, name, animal);
            } else {
                petService.editPet(id, name, photo, animal);
            }


        } catch (Exception e) {

            attributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/pets/pet-edit");
        }
        attributes.addFlashAttribute("succes", "La mascota se editó con éxito!");
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
