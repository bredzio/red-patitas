package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Transactional
    public void create(String city) {

        Zone zone = new Zone();
        zone.setCity(city);

        zoneRepository.save(zone);
    }

    @Transactional(readOnly = true)
    public List<Zone> findAll(){
        return zoneRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Zone> findById(Integer id){
        return zoneRepository.findById(id);
    }
}
