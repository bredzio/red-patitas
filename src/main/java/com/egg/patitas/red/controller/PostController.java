package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.service.PetService;
import com.egg.patitas.red.service.PostService;
import com.egg.patitas.red.service.UserService;
import com.egg.patitas.red.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/posts")
public class PostController {


    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private PetService petService;

    @GetMapping
    public ModelAndView showAll(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("posts");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
//            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("posts", postService.findAll());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createPost(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("post-form");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            //mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error"));
        } else {
            mav.addObject("post", new Post());
        }

//        mav.addObject("users", userService.findAll());
        mav.addObject("pets",petService.findAll()); //pensar como traer pets solo del usuario loggeado
        mav.addObject("zones", zoneService.findAll());
        mav.addObject("title", "Crear Post");
        mav.addObject("action", "save");
        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@ModelAttribute Post post, RedirectAttributes attributes)  {
        RedirectView redirectView = new RedirectView("/posts");

        try {
            postService.createPost(post);
        }catch(Exception e){
            attributes.addFlashAttribute("post", post);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/post/create");
        }

        return redirectView;
    }

}
