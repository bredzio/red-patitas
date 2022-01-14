package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Role;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

    @Transactional
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
