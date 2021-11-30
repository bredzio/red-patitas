package com.egg.patitas.red.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String city;

    public Zone() {
    }

    public Zone(Integer id, String city) {
        this.id = id;
        this.city = city;
    }
}
