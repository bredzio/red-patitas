package com.egg.patitas.red.service;


import com.egg.patitas.red.model.Animal;
import com.egg.patitas.red.model.Pet;
import com.egg.patitas.red.model.Post;
import com.egg.patitas.red.model.User;
import com.egg.patitas.red.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @Transactional
    public void createPet(String name, MultipartFile photo, Animal animal, String email) throws Exception{

        if(name==null || name.isEmpty()){
            throw new Exception("El nombre no puede ser nulo");
        }

        if(photo==null || photo.isEmpty()){
            throw new Exception("Tiene que subir una foto");
        }

        if(animal==null){
            throw new Exception("Tiene que seleccionar un animal");
        }

        Pet pet = new Pet();
        pet.setAnimal(animal);
        pet.setUser(userService.findByEmail(email));
        pet.setEnabled(true);
        pet.setName(name.toLowerCase());
        pet.setPhoto(photoService.copy(photo));
        petRepository.save(pet);

    }

    @Transactional
    public void editPet(Integer id, String name, MultipartFile photo,Animal animal) throws Exception{

        if(id==null){
            throw new Exception("El id no puede ser nulo");
        }

        if(name==null || name.isEmpty()){
            throw new Exception("El nombre no puede ser nulo");
        }

        if(photo==null || photo.isEmpty()){
            throw new Exception("Debe ingresar una foto de su mascota");
        }

        if(animal==null){
            throw new Exception("Debe seleccionar un animal");
        }

        Optional<Pet> answer = petRepository.findById(id);

        if(answer.isPresent()){
            Pet pet = answer.get();
            pet.setAnimal(animal);
            pet.setEnabled(true);
            pet.setName(name.toLowerCase());
            pet.setPhoto(photoService.copy(photo));
            //pet.setPhoto(storageService.uploadFile(photo));
            petRepository.save(pet);

        }else{
            throw new Exception("No se encontró la pet solicitada");
        }


    }


    @Transactional
    public void editPet(Integer id, String name,Animal animal) throws Exception{

        if(id==null){
            throw new Exception("El id no puede ser nulo");
        }

        if(name==null || name.isEmpty()){
            throw new Exception("El nombre no puede ser nulo");
        }


        if(animal==null){
            throw new Exception("Tiene que seleccionar un animal");
        }

        Optional<Pet> answer = petRepository.findById(id);

        if(answer.isPresent()){
            Pet pet = answer.get();
            pet.setAnimal(animal);
            pet.setEnabled(true);
            pet.setName(name.toLowerCase());
            //pet.setPhoto(photoService.copy(photo));
            petRepository.save(pet);

        }else{
            throw new Exception("No se encontró la pet solicitada");
        }


    }



    @Transactional
    public void deletePet(Integer id) throws Exception{
        if(id==null){
            throw new Exception("El id no puede ser nulo");
        }

        Optional<Pet> answer = petRepository.findById(id);

        if(answer.isPresent()){

            Pet pet = answer.get();
            pet.setEnabled(false);

            petRepository.save(pet);
        }else{
            throw new Exception("No se encontró la pet solicitada");
        }
    }

    @Transactional
    public void enabledPet(Integer id) throws Exception{
        if(id==null){
            throw new Exception("El id no puede ser nulo");
        }
        Optional<Pet> answer = petRepository.findById(id);
        if(answer.isPresent()){
            Pet pet = answer.get();
            pet.setEnabled(true);
            petRepository.save(pet);
        }else{
            throw new Exception("No se encontró el pet solicitado");
        }
    }

    @Transactional
    public List<Pet> findAll(){
        return petRepository.findAll();
    }

    @Transactional
    public List<Pet> findByUser(Integer id){
        return petRepository.findAll();
    }

    @Transactional
    public List<Pet> listPet() throws Exception{
        List<Pet> pets = new ArrayList();
        List<Pet> petsEnabled = new ArrayList();

        pets = petRepository.findAll();

        for(Pet p : pets){
            if(p.getEnabled()==true){
                petsEnabled.add(p);
            }
        }

        if(petsEnabled.isEmpty()){
            throw new Exception("No hay pets disponibles");
        }else{
            return petsEnabled;
        }
    }

    @Transactional
    public Pet findById(Integer id) {
        return petRepository.findById(id).orElse(null);
    }



    @Transactional
    public List<Pet> findByUserEmail(String email) {
        return petRepository.findByUser_Email(email);

    }
}
