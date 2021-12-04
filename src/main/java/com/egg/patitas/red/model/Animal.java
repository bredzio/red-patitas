package com.egg.patitas.red.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "animal", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Animal SET enabled = false WHERE id = ?")
public class Animal {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "animal name cannot be null")
    @NotEmpty(message = "animal name cannot be empty")
    private String name;

    //Verificar relacion con pets
    @OneToMany(mappedBy = "animal")
    private List<Pet> pets;

    private Boolean enabled = true;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateCreation;

    @LastModifiedDate
    private LocalDateTime dateModified;
}
