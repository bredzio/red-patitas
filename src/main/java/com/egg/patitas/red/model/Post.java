package com.egg.patitas.red.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SQLDelete(sql = "UPDATE `post` SET enabled = false WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nullable
    @ManyToOne
    private Zone zone;

    @Nullable
    @ManyToOne
    private User user;

    //    agregado para acceder directo a pets
    @Nullable
    @OneToOne
    private Pet pet;

    @NotEmpty(message = "La descrpcion es obligatoria")
    @NotNull(message = "La descripcion no puede ser nulo")
    private String description;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime dateModified;

    @LastModifiedDate
    private LocalDateTime dateLostOrFound;

    private Boolean enabled;
    private Boolean lostOrFound;

}
