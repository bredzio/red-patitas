package com.egg.patitas.red.service;

import com.egg.patitas.red.config.RoleEnum;
import com.egg.patitas.red.email.EmailSend;
import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.Role;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.repository.RoleRepository;
import com.egg.patitas.red.repository.UserRepository;
import com.egg.patitas.red.security.SecurityUtils;
import com.egg.patitas.red.security.token.TokenConfirmation;
import com.egg.patitas.red.security.token.TokenConfirmationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSend emailSend;
    private final TokenConfirmationService tokenConfirmationService;
    private final String CONFIRM="http://localhost:8080/auth/register/confirm?token=";


    @Transactional
    public void createUser(User dto) throws EmailExistException, MessagingException {
        if (userRepository.findByEmail(dto.getEmail()) != null) throw new EmailExistException(dto.getEmail());
        userRepository.save(buildUser(dto));
    }

    @Transactional
    public User buildUser(User dto) throws EmailExistException, MessagingException {
        User user = new User();

        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setEnabled(false);
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        if (roleRepository.findAll().isEmpty()) {
            Role roleUser = new Role();
            Role roleAdmin = new Role();

            roleUser.setName("USER");
            roleAdmin.setName("ADMIN");

            roleRepository.save(roleUser);
            roleRepository.save(roleAdmin);
        }

        if (userRepository.findAll().isEmpty()) {
            user.setRole(roleService.findByName(RoleEnum.ADMIN.getName()));
        } else {
            user.setRole(roleService.findByName(RoleEnum.USER.getName()));
        }

        userRepository.save(user);

        String link = CONFIRM + buildToken(user);

        emailSend.sendWelcomeEmail(user.getEmail(),user.getName(),user.getLastname(), link);
        return user;
    }

    @Transactional
    public String confirmToken(String token) {
        TokenConfirmation tokenConfirmation = tokenConfirmationService.getToken(token).orElseThrow(() -> new IllegalStateException("Token no encontrado"));

        if (tokenConfirmation.getConfirmedAt() != null) throw new IllegalStateException("Email confirmado");

        LocalDateTime expiredAt = tokenConfirmation.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) throw new IllegalStateException("Expiró token");

        tokenConfirmationService.setConfirmedAt(token);
        enableAppUser(tokenConfirmation.getUser().getEmail());

        return "Cuenta confirmada";
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }


    public String buildToken(User user) {

        String token = UUID.randomUUID().toString();

        TokenConfirmation tokenConfirmation = new TokenConfirmation(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),user);

        tokenConfirmationService.saveConfirmationToken(tokenConfirmation);

        return token;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (email == null) throw new UsernameNotFoundException("Usuario no encontrado");

        if (!user.getEnabled()) throw new UsernameNotFoundException("Usuario inhabilitado");

        GrantedAuthority authority = SecurityUtils.convertToAuthority(user.getRole().getName());

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attr.getRequest().getSession(true);

        session.setAttribute("id", user.getId());
        session.setAttribute("name", user.getName().toUpperCase(Locale.ROOT));
        session.setAttribute("email", user.getEmail());
        session.setAttribute("rol", user.getRole().getName());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(authority));
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Optional<User> findById(Integer id){return userRepository.findById(id);}

    @Transactional
    public void edit(Integer id, String name, String lastname, String email, String password) {
        userRepository.modificar(id,name,lastname,email,bCryptPasswordEncoder.encode(password));
    }

    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void enabled(Integer id) {
        userRepository.enabled(id);
    }

}
