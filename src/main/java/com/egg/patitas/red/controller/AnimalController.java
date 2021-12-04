package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.Animal;
import com.egg.patitas.red.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    AnimalService animalService;

    @GetMapping
    public ModelAndView showAll(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("animals");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
//            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("title", "Animals");
        mav.addObject("animals", animalService.findAll());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView create(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("animal-form");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
//            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("animal", new Animal());
        mav.addObject("title", "Crear Animal");
        mav.addObject("action", "save");
        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String name, RedirectAttributes attributes)  {
        RedirectView redirectView = new RedirectView("/animals");
        try{
            animalService.createAnimal(name);
            redirectView.setUrl("/animals");
        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/animals/create");
        }

        return redirectView;
    }

}
