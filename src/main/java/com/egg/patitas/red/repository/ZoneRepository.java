package com.egg.patitas.red.repository;

import com.egg.patitas.red.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ZoneRepository extends JpaRepository<Zone,Integer> {

@Modifying
@Query("UPDATE Zone z SET z.enabled=true WHERE z.id = :id")
    void enable(@Param("id") Integer id);

@Modifying
    @Query("UPDATE Zone z SET z.enabled=false WHERE z.id=:id")
    void disable(@Param("id") Integer id);



}
