package com.egg.patitas.red.service;

import com.egg.patitas.red.config.RoleEnum;
import com.egg.patitas.red.email.EmailSend;
import com.egg.patitas.red.exception.EmailExistException;
import com.egg.patitas.red.model.Role;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.repository.RoleRepository;
import com.egg.patitas.red.repository.UserRepository;
import com.egg.patitas.red.security.token.TokenConfirmation;
import com.egg.patitas.red.security.token.TokenConfirmationService;
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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailSend emailSend;

    @Autowired
    private TokenConfirmationService tokenConfirmationService;


    @Transactional
    public void createUser(User dto) throws EmailExistException {
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

        emailSend.send((dto.getEmail()), buildEmail(dto.getName()));
        return user;
    }


    public String enableUser(User user) {

        String token = UUID.randomUUID().toString();

        TokenConfirmation tokenConfirmation = new TokenConfirmation(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),user);

        tokenConfirmationService.saveConfirmationToken(tokenConfirmation);

        return token;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (email == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        if (!user.getEnabled()) {
            throw new UsernameNotFoundException("Usuario dado de baja");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER" + user.getRole().getName());

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attr.getRequest().getSession(true);

        session.setAttribute("id", user.getId());
        session.setAttribute("name", user.getName());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("rol", user.getRole().getName());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(authority));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private String buildEmail(String name) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirme su email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hola " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Muchas gracias por registrarse. Por favor, apriete sobre el boton para activar su cuenta: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=>Activar Ahora</a> </p></blockquote>\n El link expira en 15 minutos. <p>¡Nos vemos!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";

    }
}
