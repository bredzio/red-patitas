package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/*@Controller
@RequestMapping("/zones")
public class ZoneController {

    @Autowired
    ZoneService zoneService;

    @GetMapping
    public ModelAndView showAll(){
        ModelAndView mav = new ModelAndView("zones");
        //mav.addObject("zones", zoneService.findAll());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createPost() {
        ModelAndView mav = new ModelAndView("zone-form");

        mav.addObject("zone", new Zone());
        mav.addObject("title", "Crear Zona");
        mav.addObject("action", "save");
        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String city)  {

        //zoneService.create(city);
        return new RedirectView("/zones");
    }
}
*/