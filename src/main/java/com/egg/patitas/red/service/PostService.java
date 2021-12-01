package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;




}
