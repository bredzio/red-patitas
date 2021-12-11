package com.egg.patitas.red.service;

import com.egg.patitas.red.model.Animal;
import com.egg.patitas.red.model.Pet;
import com.egg.patitas.red.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> findAll() {

        return animalRepository.findAll();
    }

    @Transactional
    public void createAnimal(String name) throws Exception {

        isValidUsername(name);

        Animal animal = new Animal();
        animal.setName(name);
        animalRepository.save(animal);
    }

    @Transactional
    public void modifyAnimal(Integer id, String name, List<Pet> pet)  throws Exception{

        Animal animal = animalRepository.findById(id).get();
        isValidUsername(name);
        animal.setName(name);
        animal.setPets(pet);
        animalRepository.save(animal);
    }

    @Transactional
        public void deleteAnimal(Integer id) throws Exception{
            if(id==null){
                throw new Exception("El id no puede ser nulo");
            }
            Optional<Animal> answer = animalRepository.findById(id);
            if(answer.isPresent()){
                Animal animal = answer.get();
                animal.setEnabled(false);
                animalRepository.save(animal);
            }else{
                throw new Exception("No se encontró el animal solicitado");
            }
        }

    @Transactional
    public void enabledAnimal(Integer id) throws Exception{
        if(id==null){
            throw new Exception("El id no puede ser nulo");
        }
        Optional<Animal> answer = animalRepository.findById(id);
        if(answer.isPresent()){
            Animal animal = answer.get();
            animal.setEnabled(true);
            animalRepository.save(animal);
        }else{
            throw new Exception("No se encontró el animal solicitado");
        }
    }



        @Transactional
        public List<Animal> listAnimal() throws Exception{
            List<Animal> animals = new ArrayList<>();
            List<Animal> animalsEnabled = new ArrayList();

            animals = animalRepository.findAll();

            for(Animal a : animals){
                if(a.getEnabled() == true){
                    animalsEnabled.add(a);
                }
            }

            if(animalsEnabled.isEmpty()){
                throw new Exception("No hay pets disponibles");
            }else{
                return animalsEnabled;
            }
        }

        


        public void isValidUsername(String name) throws Exception {
            if (name == null || name.trim().isEmpty()) {
                throw new Exception("El nombre de la ciudad no puede ser nulo");
            }
            String regex = "^[a-zA-Z]{3,16}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(name);
            if (!m.find()){
                throw new Exception("El nombre no es valido, tiene que solo letras sin espacios entre 3 y 16 caracteres");
            }
        }


    }
