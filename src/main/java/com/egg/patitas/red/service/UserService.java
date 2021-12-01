package com.egg.patitas.red.service;

import com.egg.patitas.red.config.RoleEnum;
import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createUser(User user)  throws EmailExistException {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new EmailExistException(user.getEmail());
            }
            userRepository.save(buildUser(user));
    }

    private User buildUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName(RoleEnum.USER.getRoleName()));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
