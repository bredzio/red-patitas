package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Transactional
    public void create(String city, String province, Integer zipCode) {
        Zone zone = new Zone();
        zone.setCity(city);
        zone.setProvince(province);
        zone.setZipCode(zipCode);
        zone.setPosts(Collections.emptyList());
        zoneRepository.save(zone);
    }


    @Transactional
    public void modify(Integer id, String city, String province, Integer zipCod){
        Zone zone = zoneRepository.findById(id).get();

        zone.setCity(city);
        zone.setProvince(province);
        zone.setZipCode(zipCod);
    }


    @Transactional(readOnly = true)
    public List<Zone> findAll(){
        return zoneRepository.findAll();
    }

    @Transactional
    public void enabled(Integer id) {zoneRepository.enabled(id);
    }

    @Transactional
    public void disable(Integer id) {zoneRepository.disable(id);
    }
}

