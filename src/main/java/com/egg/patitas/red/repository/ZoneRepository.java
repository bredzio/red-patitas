package com.egg.patitas.red.repository;

import com.egg.patitas.red.model.User;
import com.egg.patitas.red.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone,Integer> {
    List<Zone> findAll();
}
