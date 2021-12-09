package com.egg.patitas.red.repository;

import com.egg.patitas.red.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
