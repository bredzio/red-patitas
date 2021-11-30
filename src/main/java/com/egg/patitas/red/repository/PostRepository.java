package com.egg.patitas.red.repository;

import com.egg.patitas.red.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Modifying
    @Query("UPDATE posts p SET p.enabled = true WHERE p.id = :id")
    void enabled(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE posts p SET p.lostOrFound = true WHERE p.id = :id")
    void found(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE posts p SET p.lostOrFound = false WHERE p.id = :id")
    void lost(@Param("id") Integer id);
}
