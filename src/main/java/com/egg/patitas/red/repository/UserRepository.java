package com.egg.patitas.red.repository;

import com.egg.patitas.red.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Optional<User> findById(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE User a " + "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Modifying
    @Query("UPDATE User u SET u.enabled = true WHERE u.id = :id")
    void enabled(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.lastname = :lastname, u.email = :email, u.password = :password WHERE u.id = :id")
    void modificar(@Param("id") Integer id, @Param("name") String name, @Param("lastname") String lastname, @Param("email") String email, @Param("password") String password);


}
