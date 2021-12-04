package com.egg.patitas.red.service;

import com.egg.patitas.red.config.RoleEnum;
import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.Role;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.repository.RoleRepository;
import com.egg.patitas.red.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    RoleService roleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createUser(User dto)  throws EmailExistException {
//        no es dto , solo lo puso de nombre por si en el futuro usamos dto
       if (userRepository.findByEmail(dto.getEmail()) != null) {
                throw new EmailExistException(dto.getEmail());
            }

        userRepository.save(buildUser(dto));

    }

    @Transactional
    public User buildUser(User dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setEnabled(true);
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        if(roleRepository.findAll().isEmpty()){
            Role roleUser = new Role();
            Role roleAdmin = new Role();

            roleUser.setName("USER");
            roleAdmin.setName("ADMIN");

            roleRepository.save(roleUser);
            roleRepository.save(roleAdmin);
        }

        if(userRepository.findAll().isEmpty()){
            user.setRole(roleService.findByName(RoleEnum.ADMIN.getName()));
        }else{
            user.setRole(roleService.findByName(RoleEnum.USER.getName()));
        }

        userRepository.save(user);
        System.out.println(user.getId());

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(email == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        if(!user.getEnabled()){
            throw new UsernameNotFoundException("Usuario dado de baja");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER"+ user.getRole().getName());

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

//        HttpSession sesion = attr.getRequest().getSession(true); //para que sirve realmente
        HttpSession session = attr.getRequest().getSession(true);

//        sesion.setAttribute("id", user.getId());
//        sesion.setAttribute("name", user.getName());
//        sesion.setAttribute("email", user.getEmail());
//        sesion.setAttribute("rol", user.getRole().getName());
        //si es sesion o session no importa, en thymelef siempre session

        session.setAttribute("id", user.getId());
        session.setAttribute("name", user.getName());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("rol", user.getRole().getName());
        session.setAttribute("pets", user.getPets());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), Collections.singletonList(authority));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
