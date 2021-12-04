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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/zones")
public class ZoneController {

    @Autowired
    ZoneService zoneService;

    @GetMapping
    public ModelAndView showAll(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("zones");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
//            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("zones", zoneService.findAll());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView create(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("zone-form");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
//            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("zone", new Zone());
        mav.addObject("title", "Crear Zona");
        mav.addObject("action", "save");
        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam String city, @RequestParam String province, @RequestParam Integer zipCode, RedirectAttributes attributes)  {
        RedirectView redirectView = new RedirectView("/zones");
        try{
            zoneService.create(city,province,zipCode);
            redirectView.setUrl("/zones");
        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/zones/create");
        }

        return redirectView;
    }
}
