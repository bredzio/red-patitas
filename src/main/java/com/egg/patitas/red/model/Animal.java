package com.egg.patitas.red.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "animals", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE animals SET enabled = false WHERE id = ?")
public class Animal<Pet> {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "animal name cannot be null")
    @NotEmpty(message = "animal name cannot be empty")
    private String name;

    //Verificar relacion con pets
    @OneToMany(mappedBy = "animal")
    private List<Pet> pets;

    private boolean enabled=true;

    @CreatedDate
    private LocalDateTime dateCreation;

    @LastModifiedDate
    private LocalDateTime dateModified;
}
