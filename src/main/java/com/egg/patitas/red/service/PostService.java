package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public void create(Zone zone, User user, String commentary, Boolean lostOrFound){
        Post post = new Post();

        post.setZone(zone);
        post.setUser(user);
        post.setCommentary(commentary);
        post.setEnabled(true); // post habilitado
        post.setLostOrFound(lostOrFound); //depende lo que el usuario elija: false = perdido , true encontrado

        postRepository.save(post);
    }

    @Transactional
    public void modify(Integer id, Zone zone, User user, String commentary, Boolean lostOrFound ){

        Post post = postRepository.findById(id).orElse(null);

        post.setZone(zone);
        post.setUser(user); //no quiero modificar el usuario, pero debo ponerlo para acceder al animal que quiero postear
        post.setCommentary(commentary);
        post.setEnabled(true); // post habilitado
        post.setLostOrFound(lostOrFound); //false = perdido

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(Integer id){
        return postRepository.findById(id);
    }

    @Transactional
    public void delete(Integer id) {
        postRepository.deleteById(id); //enable = false
    }

    @Transactional
    public void enabled(Integer id) {
        postRepository.enabled(id);
    }

    @Transactional
    public void foundTrue(Integer id) {
        postRepository.found(id);
    }

    @Transactional
    public void foundFalse(Integer id) {
        postRepository.lost(id);
    }
}
