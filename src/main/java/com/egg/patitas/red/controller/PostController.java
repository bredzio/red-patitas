package com.egg.patitas.red.controller;

import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.service.PostService;
import com.egg.patitas.red.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    ZoneService zoneService;

    @GetMapping
    public ModelAndView showAll(){
        ModelAndView mav = new ModelAndView("posts");
        mav.addObject("posts", postService.findAll());
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView createPost() {
        ModelAndView mav = new ModelAndView("post-form");

        mav.addObject("post", new Post());
        mav.addObject("users", userService.findAll()); // lista animal en html
        mav.addObject("zones", zoneService.findAll());
        mav.addObject("title", "Crear Post");
        mav.addObject("action", "save");
        return mav;
    }

   @GetMapping("/edit/{id}")
    public ModelAndView modifyPost(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("post-form");
        mav.addObject("post", postService.findById(id));
        mav.addObject("users", userService.findAll());
        mav.addObject("zones", zoneService.findAll());
        mav.addObject("title", "Editar Post");
        mav.addObject("action", "modify");
        return mav;
    }

    @PostMapping("/save")
    public RedirectView save(@RequestParam Zone zone, @RequestParam User user, @RequestParam String commentary, @RequestParam Boolean lostOrFound)  {

        postService.create(zone, user,commentary, lostOrFound);
        return new RedirectView("/posts");
    }

    @PostMapping("/modify")
    public RedirectView modify(@RequestParam Integer id, @RequestParam Zone zone, @RequestParam User user, @RequestParam String commentary, @RequestParam Boolean lostOrFound)  {

        postService.modify(id,zone, user,commentary, lostOrFound);
        return new RedirectView("/posts");
    }

    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        postService.delete(id);
        return new RedirectView("/posts");
    }

    @PostMapping("/enabled/{id}")
    public RedirectView enabled(@PathVariable Integer id) {
        postService.enabled(id);
        return new RedirectView("/posts");
    }

//    @PostMapping("/found/{id}")
//    public RedirectView found(@PathVariable Integer id) {
//        postService.foundTrue(id);
//        return new RedirectView("/post-form");
//    }
//
//    @PostMapping("/lost/{id}")
//    public RedirectView lost(@PathVariable Integer id) {
//        postService.foundFalse(id);
//        return new RedirectView("/post-form");
//    }
}
