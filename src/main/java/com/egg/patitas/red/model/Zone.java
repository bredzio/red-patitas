package com.egg.patitas.red.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Zone z SET false where z.id = ? ")
public class Zone {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;


    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String Province;

    @Column(nullable = false)
    private Integer zipCode;

    //Verificar la relacion
    @OneToMany(mappedBy = "zone")
    private List<Post> posts;

    @Column(nullable = false)
    private Boolean alta;
}
