package com.egg.patitas.red.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@SQLDelete(sql = "UPDATE User SET enabled = false WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Nombre es obligatorio")
    @NotNull(message = "Nombre no puede ser nulo")
    private String name;

    @NotEmpty(message = "Apellido es obligatorio")
    @NotNull(message = "Apellido no puede ser nulo")
    private String lastname;

    @NotEmpty(message = "Correo electrónico obligatorio")
    @NotNull(message = "Correo electrónico no puede ser nulo")
    @Email(message = "Correo electrónico ingresado inválido.", regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    @Length(max = 80)
    private String email;

    @NotEmpty(message = "Contraseña obligatoria")
    @NotNull(message = "Contraseña no puede ser nula")
    @Length(min = 5, message = "Ingrese una contraseña de minímo 5 carácteres")
    private String password;

    private Boolean enabled;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;

    @Nullable
    @OneToMany (mappedBy = "user")
    private List<Post> posts;

    @OneToMany (mappedBy = "user")
    @Nullable
    private List<Pet> pets;

    @ManyToOne
    @JoinColumn(name="role")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}


