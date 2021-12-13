package com.egg.patitas.red.service;

import com.egg.patitas.red.exception.MyException;
import com.egg.patitas.red.model.Pet;
import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.repository.PostRepository;
import com.egg.patitas.red.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

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
        post.setDescription(dto.getDescription().toLowerCase());
        post.setEnabled(true); // post habilitado
        post.setLostOrFound(dto.getLostOrFound());

        postRepository.save(post);
        System.out.println(post.getId());

        return post;
    }

    @Transactional
    public void modify(Post dto) throws MyException {

        Optional<Post> answer = postRepository.findById(dto.getId());
        if(answer.isPresent()){
            Post post = answer.get();
            post.setZone(dto.getZone());
            post.setUser(dto.getUser());
            post.setPet(dto.getPet());
            post.setDescription(dto.getDescription().toLowerCase());
            post.setLostOrFound(dto.getLostOrFound());

            postRepository.save(post);

        }else{
            throw new MyException("No se encontró el pet solicitado");
        }

    }

    @Transactional
    public List<Post> findAll(){
        return postRepository.findAll();

    }

    @Transactional
    public List<Post> findByUser(String email){
        return postRepository.findByUser_Id(userRepository.findByEmail(email).getId());
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(Integer id){
        return postRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Post> findLostPost(){

        List <Post> lostpost = new ArrayList<>();

        for (Post p : findAll()) {
            if (p.getLostOrFound() == false) {
                lostpost.add(p);
            }
        }
        return lostpost;
    }

    @Transactional(readOnly = true)
    public List<Post> findFoundPost(){

        List <Post> foundpost = new ArrayList();

        for (Post p : findAll()) {
            if (p.getLostOrFound() == true) {
                foundpost.add(p);
            }
        }
        return foundpost;
    }


    @Transactional
    public void delete(Integer id) {
        postRepository.deleteById(id); //enable = false
    }

    @Transactional
    public void enabled(Integer id) {
        postRepository.enabled(id);
    }
}
