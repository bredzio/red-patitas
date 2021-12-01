package com.egg.patitas.red.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@AllArgsConstructor
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE Role SET enabled = false WHERE id = ?")
public class Role {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Integer id;

    @NotNull(message = "role name cannot be null")
    @NotEmpty(message = "role name cannot be empty")
    private String name;

    private boolean enabled=true;

}