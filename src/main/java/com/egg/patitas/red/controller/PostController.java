package com.egg.patitas.red.controller;

import com.egg.patitas.red.exception.MyException;
import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.service.PetService;
import com.egg.patitas.red.service.PostService;
import com.egg.patitas.red.service.UserService;
import com.egg.patitas.red.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("posts", postService.findAll());
        return mav;
    }

    @GetMapping("/lostposts")
    public ModelAndView showLostPost(){
        ModelAndView mav = new ModelAndView("posts");
        mav.addObject("posts", postService.findLostPost());
        mav.addObject("title","Mascotas perdidas");

        return mav;
    }

    @GetMapping("/foundposts")
    public ModelAndView showFoundPost(){
        ModelAndView mav = new ModelAndView("posts");

        mav.addObject("posts", postService.findFoundPost());
        mav.addObject("title","Mascotas encontradas");

        return mav;
    }



    @GetMapping("/create")
    public ModelAndView createPost(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView("post-form");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            //mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error"));
        } else {
            mav.addObject("post", new Post());
        }

        String email=(String) session.getAttribute("email");
//        mav.addObject("users", userService.findByEmail(email));
        mav.addObject("pets",petService.findByUserEmail(email));
        mav.addObject("zones", zoneService.findAll());
        mav.addObject("title", "Nuevo Post");
        mav.addObject("action", "save");
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView modifyPost(@PathVariable Integer id, HttpServletRequest request, HttpSession session, RedirectAttributes attributes) {
        if (!session.getAttribute("id").equals(id)) {
            return new ModelAndView(new RedirectView("/"));
        }

        ModelAndView mav = new ModelAndView("post-form");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

            if (flashMap != null) {
                mav.addObject("success", flashMap.get("success"));
                mav.addObject("error", flashMap.get("error"));
                mav.addObject("post", flashMap.get("post"));
            } else {
                mav.addObject("post", postService.findById(id));
                String email=(String) session.getAttribute("email");
                mav.addObject("users", userService.findByEmail(email));
                mav.addObject("pets",petService.findByUserEmail(email));
                mav.addObject("zones", zoneService.findAll());

            }

        mav.addObject("title", "Editar Post");
        mav.addObject("action", "modify");
        return mav;

    }

    @PostMapping("/save")
    public RedirectView save(@ModelAttribute Post post, RedirectAttributes attributes, HttpSession session)  {
        RedirectView redirectView = new RedirectView("/posts");

        try {
            String email=(String) session.getAttribute("email");
            postService.createPost(post, email);
        }catch(Exception e){
            attributes.addFlashAttribute("post", post);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/post/create");
        }

        return redirectView;
    }

    @PostMapping("/modify")
    public ModelAndView modify(@Valid @ModelAttribute Post post, BindingResult result, RedirectAttributes attributes)  {

        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("title", "Editar Post");
            mav.addObject("action", "modify");
            mav.addObject("post", post);
            mav.setViewName("post-form");
            return mav;
        }

        try {
            postService.modify(post);
            attributes.addFlashAttribute("success", "La actualización ha sido realizada satisfactoriamente");
            mav.setViewName("redirect:/posts");
        } catch (MyException e) {
            attributes.addFlashAttribute("post", post);
            attributes.addFlashAttribute("error", e.getMessage());
            mav.setViewName("redirect:/post/edit/" + post.getId());
        }

        return mav;
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

}
