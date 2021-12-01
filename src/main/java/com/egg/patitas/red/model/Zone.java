package com.egg.patitas.red.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import java.util.List;

import javax.persistence.*;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table(name = "zones")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE zones z SET false where z.id = ? ")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String province;


    @NotNull
    @NotEmpty
    private Integer zipCode;

    //Verificar la relacion
    @OneToMany(mappedBy = "zone")
    private List<Post> posts;

    @NotNull
    @NotEmpty
    private Boolean enabled;
}
