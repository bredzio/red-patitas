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


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Zone z SET z.enabled = false where z.id = ? ")
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

    @OneToMany(mappedBy="zone")
    private List<Post> posts;


    @NotNull
    private Integer zipCode;



    private Boolean enabled;
}
