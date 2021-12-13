package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Zone;
import com.egg.patitas.red.repository.PostRepository;
import com.egg.patitas.red.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egg.patitas.red.model.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private PostService postService;

    @Transactional
    public List<Zone> findAll() {
        List<Zone> zones = zoneRepository.findAll();
        for(Zone z: zones) {
            z.setNumberOfPosts(getPostsForZone(z.getId()).size());
        }
        return zones;
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
        zone.setEnabled(true);
        zoneRepository.save(zone);
    }

    @Transactional
    public void modify(Integer id, String city, String province, Integer zipCod){

        Zone zone = zoneRepository.findById(id).get();

        zone.setCity(city.toLowerCase());
        zone.setProvince(province);
        zone.setZipCode(zipCod);

        zoneRepository.save(zone);
    }

    @Transactional(readOnly = true)
    public Zone findById(Integer id) throws Exception{
        Optional<Zone> zone = zoneRepository.findById(id);

        if(!zone.isPresent()) {
            throw new Exception("no se encuentra la zona con id "+ id);
        }


        return zone.get();
    }

    @Transactional(readOnly = true)
    public List<Post> findPostsByIdZone(Integer id) throws Exception{
        Optional<Zone> zone = zoneRepository.findById(id);

        if(!zone.isPresent()) {
            throw new Exception("no se encuentra la zona con id "+ id);
        }

        return getPostsForZone(id);
    }

    @Transactional(readOnly = true)
    public List<Post> findLostPostsByIdZone(Integer id) throws Exception {
        Optional<Zone> zone = zoneRepository.findById(id);

        if (zone == null) {
            throw new Exception("No se encuentra la zona con id " + id.toString());
        }

        List <Post> lostpost = new ArrayList();

        for (Post p : getPostsForZone(id)) {
            if (p.getLostOrFound() == false) {
                lostpost.add(p);
            }
        }
        return lostpost;

    }



    @Transactional(readOnly = true)
    public List<Post> findFoundPostByIdZone(Integer id) throws Exception{

        Zone zone = zoneRepository.findById(id).get();

        if (zone == null) {
            throw new Exception("No se encuentra la zona con id " + id.toString());
        }

        List <Post> foundpost = new ArrayList();

        for (Post p : getPostsForZone(id)) {
            if (p.getLostOrFound() == true) {
                foundpost.add(p);
            }
        }
        return foundpost;
    }

    List<Post> getPostsForZone(Integer zoneId) {
        return postService
                .findAll()
                .stream()
                .filter(p -> p.getZone().getId() == zoneId)
                .collect(Collectors.toList());
    }

    @Transactional
    public void enable(Integer id) {
        zoneRepository.enable(id);
    }

    @Transactional
    public void disable(Integer id) {
        zoneRepository.disable(id);
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

