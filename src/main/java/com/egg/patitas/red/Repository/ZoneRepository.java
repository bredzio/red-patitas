package com.egg.patitas.red.Repository;


import com.egg.patitas.red.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ZoneRepository extends JpaRepository<Zone,Integer> {
}
