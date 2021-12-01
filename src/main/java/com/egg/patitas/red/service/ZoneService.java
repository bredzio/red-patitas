package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.repository.UserRepository;
import com.egg.patitas.red.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;


    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }
}
