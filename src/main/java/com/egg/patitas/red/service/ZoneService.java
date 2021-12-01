package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.patitas.red.model.Post;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;

import java.util.List;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;


    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }


    @Transactional
    public void create(String city, String province, Integer zipCode) throws Exception {

        validateCity(city);
        validateProvince(province);
        validateZipCode(zipCode);


        Zone zone = new Zone();
        zone.setCity(city);
        zone.setProvince(province);
        zone.setZipCode(zipCode);
        //zone.setPosts(Collections.emptyList());
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
    public List<Post> findPostsByIdZone(Integer id) throws Exception{
        Zone zone = zoneRepository.findById(id).get();
        if(zone == null) {
            throw new Exception("no se encuentra la zona con id "+ id.toString());
        }
            return zone.getPosts();
    }

    @Transactional
    public void enabled(Integer id) {zoneRepository.enabled(id);
    }



    public void validateCity(String city) throws Exception {
        if (city == null || city.trim().isEmpty()){
            throw new Exception("El nombre de la ciudad no puede ser nulo");
        }
    }

    public void validateProvince(String province) throws Exception{
        if (province == null || province.trim().isEmpty()){
            throw new Exception("El nombre de la provincia no puede ser nulo");
        }
    }

    public void validateZipCode(Integer zipCode) throws Exception{
        if (zipCode< 1000 || zipCode>9999){
            throw new Exception("El Codigo postal es incorrecto");
        }
    }


}

