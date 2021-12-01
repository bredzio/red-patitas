package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Role;
import com.egg.patitas.red.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }
}
