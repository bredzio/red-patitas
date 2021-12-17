package com.egg.patitas.red.controller;

import com.egg.patitas.red.exception.MyException;
import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.security.SecurityConstant;
import com.egg.patitas.red.service.PetService;
import com.egg.patitas.red.service.PostService;
import com.egg.patitas.red.service.UserService;
import com.egg.patitas.red.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {


    @Autowired
    private PostService postService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView showAll(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("posts");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("bottom", true);
        mav.addObject("emailUser", true);
        mav.addObject("posts", postService.findAll());
        return mav;
    }

    @GetMapping("/byUser/{email}")
    @PreAuthorize(SecurityConstant.ADMIN_OR_USERAUTH)
    public ModelAndView postsByUser(@PathVariable String email){
        ModelAndView mav = new ModelAndView("posts");

        mav.addObject("title", "Tus Publicaciones");
        mav.addObject("estado", true);
        mav.addObject("bottom", true);
        mav.addObject("posts",postService.findByUser(email));
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView showById(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("post-detail");
        try {
            Post post = postService.findId(id);
            mav.addObject("post", post);
            mav.addObject("title", "Detalle de la Publicación");
        } catch (Exception e) {
            mav.addObject("error-get-post", e.getMessage());
        }
        return mav;
    }

    @GetMapping("/lostposts")
    public ModelAndView showLostPost(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("posts");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("bottom", false);
        mav.addObject("posts", postService.findLostPost());
        mav.addObject("title","Mascotas perdidas");

        return mav;
    }

    @GetMapping("/foundposts")
    public ModelAndView showFoundPost(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("posts");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("bottom", false);
        mav.addObject("posts", postService.findFoundPost());
        mav.addObject("title","Mascotas encontradas");

        return mav;
    }


    @GetMapping("/create")
    @PreAuthorize(SecurityConstant.ADMIN_AND_USER)
    public ModelAndView createPost(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView("post-form");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("success", flashMap.get("success"));
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("post", flashMap.get("post"));
        } else {
            mav.addObject("post", new Post());
        }

        String email=(String) session.getAttribute("email");
        mav.addObject("pets",petService.findByUserEmail(email));
        mav.addObject("zones", zoneService.findAll());
        mav.addObject("title", "Nueva publicación");
        mav.addObject("action", "save");
        return mav;
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize(SecurityConstant.ADMIN_AND_USER)
    public ModelAndView modifyPost(@PathVariable Integer id, HttpServletRequest request, HttpSession session, RedirectAttributes attributes) {
        Optional<Post> maybePost = postService.findById(id);

        if(!maybePost.isPresent()) {
            attributes.addFlashAttribute("error", "La publicación no existe");
            return new ModelAndView(new RedirectView("/posts/byUser/" + session.getAttribute("email")));
        }

        Post post = maybePost.get();
        if((postService.findId(id).getUser().getEmail().equals(session.getAttribute("email")) || userService.findByEmail((String) session.getAttribute("email")).getRole().getId()==2)){
            ModelAndView mav = new ModelAndView("post-form");
            Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

            if (flashMap != null) {
                mav.addObject("success", flashMap.get("success"));
                mav.addObject("error", flashMap.get("error"));
                mav.addObject("post", flashMap.get("post"));
            } else {
                mav.addObject("post", post);
                String email=(String) session.getAttribute("email");
                mav.addObject("pets",petService.findByUserEmail(email));
                mav.addObject("zones", zoneService.findAll());

            }

            mav.addObject("title", "Editar Post");
            mav.addObject("action", "modify");
            return mav;
        }else{
            attributes.addFlashAttribute("error", "No puedes editar el post de otro usuario");
            return new ModelAndView(new RedirectView("/posts/byUser/" + session.getAttribute("email")));
        }

    }

    @PostMapping("/save")
    public RedirectView save(@Valid @ModelAttribute Post post, BindingResult result, RedirectAttributes attributes, HttpSession session)  {
        RedirectView redirectView = new RedirectView("/posts/byUser/" + session.getAttribute("email"));

        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors ) {
                attributes.addFlashAttribute("error",error.getDefaultMessage());
                attributes.addFlashAttribute("post", post);
            }

            redirectView.setUrl("/posts/create");
            return redirectView;
        }

        try {
            String email=(String) session.getAttribute("email");
            postService.createPost(post, email);
        }catch(Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("post", post);
            redirectView.setUrl("/posts/create");
        }
        return redirectView;
    }


    @PostMapping("/modify")
    @PreAuthorize(SecurityConstant.ADMIN_AND_USER)
    public ModelAndView modify(@Valid @ModelAttribute Post post, BindingResult result, HttpSession session, RedirectAttributes attributes)  {

        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("title", "Editar Post");
            mav.addObject("action", "modify");
            mav.addObject("post", post);
            mav.setViewName("post-form");
            return mav;
        }

        try {
            String email=(String) session.getAttribute("email");
            postService.modify(post, email);
            attributes.addFlashAttribute("success", "La actualización ha sido realizada satisfactoriamente");
            mav.setViewName("redirect:/posts/byUser/" + session.getAttribute("email"));
        } catch (MyException e) {
            attributes.addFlashAttribute("post", post);
            attributes.addFlashAttribute("error", e.getMessage());
            mav.setViewName("redirect:/posts/edit/" + post.getId());
        }

        return mav;
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize(SecurityConstant.ADMIN_AND_USER)
    public RedirectView delete(@PathVariable Integer id, RedirectAttributes attributes, HttpSession session) {

        if(postService.findId(id).getUser().getEmail().equals(session.getAttribute("email")) || userService.findByEmail((String) session.getAttribute("email")).getRole().getId()==2){
            try {
                postService.delete(id);
                attributes.addFlashAttribute("success","Se deshabilito el post");

            } catch (Exception e) {
                attributes.addFlashAttribute("error", e.getMessage());

            }
            return new RedirectView("/posts/byUser/" + session.getAttribute("email") );
        }

        return new RedirectView("/posts/byUser/" + session.getAttribute("email"));

    }

    @PostMapping("/enabled/{id}")
    @PreAuthorize(SecurityConstant.ADMIN_AND_USER)
    public RedirectView enabled(@PathVariable Integer id, RedirectAttributes attributes, HttpSession session) {

        if(postService.findId(id).getUser().getEmail().equals(session.getAttribute("email")) || userService.findByEmail((String) session.getAttribute("email")).getRole().getId()==2){
            try {
                postService.enabled(id);
                attributes.addFlashAttribute("success","Se habilito el post");
            } catch (Exception e) {
                attributes.addFlashAttribute("error", e.getMessage());
            }
            return new RedirectView("/posts/byUser/" + session.getAttribute("email") );
        }

        return new RedirectView("/posts/byUser/" + session.getAttribute("email"));
    }

}
