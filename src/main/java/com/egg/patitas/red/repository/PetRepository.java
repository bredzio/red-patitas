package com.egg.patitas.red.repository;

import com.egg.patitas.red.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findByUser_Id(Integer id);

    List<Pet> findByUser_Email(String email);
}
