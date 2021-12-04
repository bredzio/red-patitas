package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Pet;
import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

//    @Transactional
//    public void create(Zone zone, User user, String commentary, Boolean lostOrFound){
//        Post post = new Post();
//
//        post.setZone(zone);
//        post.setUser(user);
//        post.setCommentary(commentary);
//        post.setEnabled(true); // post habilitado
//        post.setLostOrFound(lostOrFound); //depende lo que el usuario elija: false = perdido , true encontrado
//
//        postRepository.save(post);
//    }

    @Transactional
    public void createPost(Post dto) {
//        no es dto , solo lo puso de nombre por si en el futuro usamos dto

        postRepository.save(buildPost(dto));

    }

    @Transactional
    public Post buildPost(Post dto) {
        Post post = new Post();

        post.setZone(dto.getZone());
        post.setUser(dto.getUser());
        post.setPet(dto.getPet());
        post.setDescription(dto.getDescription());
        post.setEnabled(true); // post habilitado
        post.setLostOrFound(dto.getLostOrFound());

        postRepository.save(post);
        System.out.println(post.getId());

        return post;
    }

    @Transactional
    public List<Post> findAll(){
        return postRepository.findAll();
    }


}
