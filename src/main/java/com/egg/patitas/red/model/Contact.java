package com.egg.patitas.red.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "contact")
@SQLDelete(sql = "UPDATE Contact SET enabled = false WHERE id=?")
public class Contact{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Debe ingresar un nombre")
    @NotEmpty(message = "Debe ingresar un nombre")
    private String name;

    @NotNull(message = "Debe ingresar un correo electronico")
    @NotEmpty(message = "Debe ingresar un correo electronico")
    @Email(message = "Correo electrónico ingresado inválido.", regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
    @Column(nullable = false)
    private String email;

    @NotNull(message = "Debe ingresar un mensaje")
    @NotEmpty(message = "Debe ingresar un mensaje")
    @Column(nullable = false)
    private String message;

    @NotNull
    private boolean deleted = Boolean.TRUE;

    @CreatedDate
    @Column (updatable = false)
    private LocalDateTime dateCreated;

}
