package com.egg.patitas.red.repository;

import com.egg.patitas.red.model.Pet;
import com.egg.patitas.red.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    @Modifying
    @Query("UPDATE Post p SET p.enabled = true WHERE p.id = :id")
    void enabled(@Param("id") Integer id);

    List<Post> findByUser_Id(Integer id);

}
