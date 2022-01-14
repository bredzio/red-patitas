package com.egg.patitas.red.repository;

import com.egg.patitas.red.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    Optional<Animal> findByName(String name);

}
